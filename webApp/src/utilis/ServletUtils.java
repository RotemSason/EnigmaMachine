package utilis;

import Allies.AlliesManager;
import Contests.ContestManager;
import Engine.EngineImpl;
import EngineManager.EngineManager;
import jakarta.servlet.ServletContext;

public class ServletUtils {
    private static final String MAIN_ENGINE_ATTRIBUTE_NAME = "engineManager";
    private static final String CONTEST_ATTRIBUTE_NAME = "contestname";
    private static final String ALLIES_ATTRIBUTE_NAME = "alliesname";
    private static final Object engineLock = new Object();
    private static final Object contestLock = new Object();
    private static final Object alliesLock = new Object();


    /*public static EngineImpl getMainEngine(ServletContext servletContext) {

        synchronized (engineLock) {
            if (servletContext.getAttribute(MAIN_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(MAIN_ENGINE_ATTRIBUTE_NAME, new EngineImpl());
            }
        }
        return (EngineImpl) servletContext.getAttribute(MAIN_ENGINE_ATTRIBUTE_NAME);
    }*/

    public static ContestManager getContestManager(ServletContext servletContext) {

        synchronized (contestLock) {
            if (servletContext.getAttribute(CONTEST_ATTRIBUTE_NAME) == null) {

                servletContext.setAttribute(CONTEST_ATTRIBUTE_NAME, new ContestManager());
            }
        }
        return (ContestManager) servletContext.getAttribute(CONTEST_ATTRIBUTE_NAME);
    }
    public static AlliesManager getAlliesManager(ServletContext servletContext) {

        synchronized (alliesLock) {
            if (servletContext.getAttribute(ALLIES_ATTRIBUTE_NAME) == null) {

                servletContext.setAttribute(ALLIES_ATTRIBUTE_NAME, new AlliesManager());
            }
        }
        return (AlliesManager) servletContext.getAttribute(ALLIES_ATTRIBUTE_NAME);
    }

    public static EngineManager getEngineManager(ServletContext servletContext) {
        synchronized (engineLock) {
            if (servletContext.getAttribute(MAIN_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(MAIN_ENGINE_ATTRIBUTE_NAME, new EngineManager());
            }
        }
        return (EngineManager) servletContext.getAttribute(MAIN_ENGINE_ATTRIBUTE_NAME);
    }

}
