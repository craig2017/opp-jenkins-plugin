package com.opp.service;

import hudson.model.*;
import org.springframework.stereotype.Service;

/**
 * Created by ctobe on 1/7/16.
 */
@Service
public class JenkinsBuildService {

    private final Build<?, ?> build;

    public JenkinsBuildService() throws Exception {
        this.build = getCurrentBuild();
    }

    private Build<?, ?> getCurrentBuild() throws Exception {

        try {
            final Executor e = (Executor) Thread.currentThread();
            final Build<?, ?> b = (Build<?, ?>) e.getCurrentExecutable();
            if (b != null) {
               // logService.getOut().println("Found Build: " + b.getFullDisplayName());
                return b;
            }

        } catch (final Exception e) {
           // logService.getOut().println(e);
           // throw e; // allow me to run junit tests, so i can't throw an exception here because i'm not inside jenkins but i need this to initialize the wptService
        }
        return null;
    }

    public Build<?, ?> getBuild() {
        return this.build;
    }

    public String getParam(final String paramName) {
        if (getBuild() != null) {
            return getBuild().getBuildVariables().get(paramName);
        }
        return "";
    }

    public String getParamWithNull(final String paramName) {
        if (getBuild() != null) {
            return getBuild().getBuildVariables().get(paramName);
        }
        return null;
    }

    public void injectStringParam(final String name, final String value) {
        try {
            final ParametersAction pa = new ParametersAction(
                    new ParameterValue[] { new StringParameterValue(name, value) });
            build.addAction(pa);
        } catch (final Exception e) {
            //Log.getInstance().getOut().println(e);
        }
    }
}
