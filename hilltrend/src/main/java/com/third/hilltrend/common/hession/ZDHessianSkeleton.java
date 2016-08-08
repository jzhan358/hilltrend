package com.third.hilltrend.common.hession;

import com.caucho.hessian.io.*;
import com.caucho.hessian.server.HessianSkeleton;
import com.caucho.services.server.ServiceContext;
import com.third.hilltrend.api.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rui on 15/8/22.
 */
public class ZDHessianSkeleton extends HessianSkeleton {


    private static final Logger log
            = Logger.getLogger(ZDHessianSkeleton.class.getName());


    protected final transient org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean _isDebug;

    private HessianInputFactory _inputFactory = new HessianInputFactory();
    private HessianFactory _hessianFactory = new HessianFactory();

    private Object _service;

    private Class _apiClass;

    /**
     * Create a new hessian skeleton.
     *
     * @param service  the underlying service object.
     * @param apiClass the API interface
     */
    public ZDHessianSkeleton(Object service, Class<?> apiClass) {
        super(apiClass);
        this._apiClass = apiClass;

        if (service == null)
            service = this;

        _service = service;

        if (!apiClass.isAssignableFrom(service.getClass()))
            throw new IllegalArgumentException("Service " + service + " must be an instance of " + apiClass.getName());
    }

    /**
     * Create a new hessian skeleton.
     *
     * @param apiClass the API interface
     */
    public ZDHessianSkeleton(Class<?> apiClass) {
        super(apiClass);
    }

    public void setDebug(boolean isDebug) {
        _isDebug = isDebug;
    }

    public boolean isDebug() {
        return _isDebug;
    }

    public void setHessianFactory(HessianFactory factory) {
        _hessianFactory = factory;
    }

    /**
     * Invoke the object with the request from the input stream.
     *
     * @param is the Hessian input stream
     * @param os the Hessian output stream
     */
    public void invoke(InputStream is, OutputStream os)
            throws Exception {
        invoke(is, os, null);
    }

    /**
     * Invoke the object with the request from the input stream.
     *
     * @param is the Hessian input stream
     * @param os the Hessian output stream
     */
    public void invoke(InputStream is, OutputStream os,
                       SerializerFactory serializerFactory)
            throws Exception {
        boolean isDebug = false;

        if (isDebugInvoke()) {
            isDebug = true;

            PrintWriter dbg = createDebugPrintWriter();
            HessianDebugInputStream dIs = new HessianDebugInputStream(is, dbg);
            dIs.startTop2();
            is = dIs;
            HessianDebugOutputStream dOs = new HessianDebugOutputStream(os, dbg);
            dOs.startTop2();
            os = dOs;
        }

        HessianInputFactory.HeaderType header = _inputFactory.readHeader(is);

        AbstractHessianInput in;
        AbstractHessianOutput out;

        switch (header) {
            case CALL_1_REPLY_1:
                in = _hessianFactory.createHessianInput(is);
                out = _hessianFactory.createHessianOutput(os);
                break;

            case CALL_1_REPLY_2:
                in = _hessianFactory.createHessianInput(is);
                out = _hessianFactory.createHessian2Output(os);
                break;

            case HESSIAN_2:
                in = _hessianFactory.createHessian2Input(is);
                in.readCall();
                out = _hessianFactory.createHessian2Output(os);
                break;

            default:
                throw new IllegalStateException(header + " is an unknown Hessian call");
        }

        if (serializerFactory != null) {
            in.setSerializerFactory(serializerFactory);
            out.setSerializerFactory(serializerFactory);
        }

        try {
            invoke(_service, in, out);
        } finally {
            in.close();
            out.close();

            if (isDebug)
                os.close();
        }
    }

    /**
     * Invoke the object with the request from the input stream.
     *
     * @param in  the Hessian input stream
     * @param out the Hessian output stream
     */
    public void invoke(AbstractHessianInput in, AbstractHessianOutput out)
            throws Exception {
        invoke(_service, in, out);
    }

    /**
     * Invoke the object with the request from the input stream.
     *
     * @param in  the Hessian input stream
     * @param out the Hessian output stream
     */
    public void invoke(Object service,
                       AbstractHessianInput in,
                       AbstractHessianOutput out)
            throws Exception {
        ServiceContext context = ServiceContext.getContext();

        // backward compatibility for some frameworks that don't read
        // the call type first
        in.skipOptionalCall();

        // Hessian 1.0 backward compatibility
        String header;
        while ((header = in.readHeader()) != null) {
            Object value = in.readObject();

            context.addHeader(header, value);
        }

        String methodName = in.readMethod();
        int argLength = in.readMethodArgLength();

        Method method;

        method = getMethod(methodName + "__" + argLength);

        if (method == null)
            method = getMethod(methodName);

        if (method != null) {
        } else if ("_hessian_getAttribute".equals(methodName)) {
            String attrName = in.readString();
            in.completeCall();

            String value = null;

            if ("java.api.class".equals(attrName))
                value = getAPIClassName();
            else if ("java.home.class".equals(attrName))
                value = getHomeClassName();
            else if ("java.object.class".equals(attrName))
                value = getObjectClassName();

            out.writeReply(value);
            out.close();
            return;
        } else if (method == null) {
            out.writeFault("NoSuchMethodException",
                    escapeMessage("The service has no method named: " + in.getMethod()),
                    null);
            out.close();
            return;
        }

        Class<?>[] args = method.getParameterTypes();

        if (argLength != args.length && argLength >= 0) {
            out.writeFault("NoSuchMethod",
                    escapeMessage("method " + method + " argument length mismatch, received length=" + argLength),
                    null);
            out.close();
            return;
        }

        Object[] values = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            // XXX: needs Marshal object
            values[i] = in.readObject(args[i]);
        }

        Object result = null;

        try {
            //values 参数指，result
            String receiveMethodName = method.getName();
            long startTime = new Date().getTime();
            logger.info("HessianServer receive token[{}] clz[{}] method[{}] arguments[{}] ",
                    new Object[]{ZDHessianReceive.get(), _apiClass.getSimpleName(), receiveMethodName, JsonUtils.toJson(values)});
            result = method.invoke(service, values);
            long endTime = new Date().getTime();
            long time = endTime - startTime;
            if (!StringUtils.contains(receiveMethodName, "search")) {
                logger.info("HessianServer response token[{}] clz[{}] method[{}] arguments[{}] time[{}]",
                        new Object[]{ZDHessianReceive.get(), _apiClass.getSimpleName(), receiveMethodName, JsonUtils.toJson(result), time});
            } else {
                logger.info("HessianServer response token[{}] clz[{}] method[{}] time[{}]",
                        new Object[]{ZDHessianReceive.get(), _apiClass.getSimpleName(), receiveMethodName, time});
            }
            ZDHessianReceive.remove();
        } catch (Exception e) {
            logger.error("HessianServer error token["+ZDHessianReceive.get()+"]",e);
            ZDHessianReceive.remove();
            Throwable e1 = e;
            if (e1 instanceof InvocationTargetException)
                e1 = ((InvocationTargetException) e).getTargetException();

            log.log(Level.FINE, this + " " + e1.toString(), e1);

            out.writeFault("ServiceException",
                    escapeMessage(e1.getMessage()),
                    e1);
            out.close();
            return;
        }

        // The complete call needs to be after the invoke to handle a
        // trailing InputStream
        in.completeCall();

        out.writeReply(result);

        out.close();
    }

    private String escapeMessage(String msg) {
        if (msg == null)
            return null;

        StringBuilder sb = new StringBuilder();

        int length = msg.length();
        for (int i = 0; i < length; i++) {
            char ch = msg.charAt(i);

            switch (ch) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case 0x0:
                    sb.append("&#00;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(ch);
                    break;
            }
        }

        return sb.toString();
    }

    protected boolean isDebugInvoke() {
        return (log.isLoggable(Level.FINEST)
                || isDebug() && log.isLoggable(Level.FINE));
    }

    /**
     * Creates the PrintWriter for debug output. The default is to
     * write to java.util.Logging.
     */
    protected PrintWriter createDebugPrintWriter()
            throws IOException {
        return new PrintWriter(new LogWriter(log));
    }

    static class LogWriter extends Writer {
        private Logger _log;
        private StringBuilder _sb = new StringBuilder();

        LogWriter(Logger log) {
            _log = log;
        }

        public void write(char ch) {
            if (ch == '\n' && _sb.length() > 0) {
                _log.fine(_sb.toString());
                _sb.setLength(0);
            } else
                _sb.append((char) ch);
        }

        public void write(char[] buffer, int offset, int length) {
            for (int i = 0; i < length; i++) {
                char ch = buffer[offset + i];

                if (ch == '\n' && _sb.length() > 0) {
                    _log.fine(_sb.toString());
                    _sb.setLength(0);
                } else
                    _sb.append((char) ch);
            }
        }

        public void flush() {
        }

        public void close() {
        }
    }
}
