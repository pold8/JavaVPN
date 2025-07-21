package com.javavpn.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
    public static Logger createLogger(Class<?> cls) {
        Logger logger = Logger.getLogger(cls.getName());
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        logger.setLevel(Level.ALL);
        return logger;
    }
}
