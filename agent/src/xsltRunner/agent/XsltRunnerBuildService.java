package xsltRunner.agent;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.TerminationAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class XsltRunnerBuildService extends BuildServiceAdapter {
    private XsltRunner runner;

    public XsltRunnerBuildService() {
    }

    @Override
    public void afterInitialized() {
        java.io.File lineListDefinition = new java.io.File(getConfig().lineListPath);
        runner = new XsltRunner(getConfig(), new LogParser(getLogger(), getConfig().warningsAsErrors, lineListDefinition));
    }

    @Override
    public void beforeProcessStarted() {
        runner.start();
    }

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        return createProgramCommandline("cmd.exe", runner.getArgs());
    }

    @NotNull
    private XsltRunnerConfiguration getConfig() {

        return new XsltRunnerConfiguration(getAgentConfiguration(), getRunnerParameters(), getBuild());
    }

    @Override
    @NotNull
    public TerminationAction interrupt() {
        runner.stop();
        return super.interrupt();
    }

    @Override
    public void afterProcessFinished() {
        // called when process is finished, BEFORE getting return code
        runner.stop();
    }

    @Override
    public void afterProcessSuccessfullyFinished() {
        runner.optionallyCleanupAfter();
    }
}
