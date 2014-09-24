package xsltRunner.server;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import org.jetbrains.annotations.NotNull;
import xsltRunner.common.PluginConstants;

import java.util.*;

public class XsltRunnerRunType extends RunType {
    public XsltRunnerRunType(final RunTypeRegistry registry) {
        registry.registerRunType(this);
    }

    @NotNull
    @Override
    public String getType() {
        return PluginConstants.RUN_TYPE;
    }

    @Override
    @NotNull
    public String getDisplayName() {
        return PluginConstants.RUNNER_DISPLAY_NAME;
    }

    @Override
    public String getDescription() {
        return PluginConstants.RUNNER_DESCRIPTION;
    }


    @Override
    @NotNull
    public String describeParameters(@NotNull final Map<String, String> parameters)
    {
        StringBuilder sb = new StringBuilder();
        int configs_count = Integer.parseInt(parameters.get(PluginConstants.PROPERTY_CONFIGS_COUNT));
        for(int i=0; i < configs_count ; i++) {
            sb.append("\n Config " + (i+1));
            sb.append(" \n");
            sb.append("- Input: ");
            sb.append(parameters.get(PluginConstants.PROPERTY_INPUT_PATH + "_" + i));
            sb.append(" \n");
            sb.append("- Ouput: ");
            sb.append(parameters.get(PluginConstants.PROPERTY_OUTPUT_PATH + "_" + i));
            sb.append(" \n");
            sb.append("- XSL File: ");
            sb.append(parameters.get(PluginConstants.PROPERTY_XSL_PATH + "_" + i));
        }
        return sb.toString();
    }


    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor()
    {
        return new PropertiesProcessor()
        {
            public Collection<InvalidProperty> process(Map<String, String> properties)
            {
                if(noBuildTarget(properties))
                    return invalidBuildTarget();

                return Collections.emptySet();
            }

            private Set<InvalidProperty> invalidBuildTarget()
            {
                return Collections.singleton(new InvalidProperty(PluginConstants.PROPERTY_BUILD_PATH,
                                                                "Please set build path."));
            }

            private boolean noBuildTarget(Map<String, String> properties)
            {
                if(properties.containsKey(PluginConstants.PROPERTY_BUILD_PLAYER) &&
                        !properties.get(PluginConstants.PROPERTY_BUILD_PLAYER).equals(""))
                {
                    if(!properties.containsKey(PluginConstants.PROPERTY_BUILD_PATH) ||
                       properties.get(PluginConstants.PROPERTY_BUILD_PATH).equals(""))
                    {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    @Override
    public String getEditRunnerParamsJspFilePath() {
        return "editRunnerRunParameters.jsp";
    }

    @Override
    public String getViewRunnerParamsJspFilePath() {
        return "viewRunnerRunParameters.jsp";
    }

    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        Map<String,String> defaults = new HashMap<String, String>();

        defaults.put(PluginConstants.PROPERTY_CONFIGS_COUNT, "1");
        defaults.put(PluginConstants.PROPERTY_QUIT, "true");
        defaults.put(PluginConstants.PROPERTY_BATCH_MODE, "true");
        defaults.put(PluginConstants.PROPERTY_SHOW_ME, "true");
        defaults.put(PluginConstants.PROPERTY_CLEAR_OUTPUT_BEFORE, "true");
        defaults.put(PluginConstants.PROPERTY_CLEAN_OUTPUT_AFTER, "true");
        defaults.put(PluginConstants.PROPERTY_WARNINGS_AS_ERRORS, "true");
        defaults.put(PluginConstants.PROPERTY_LOG_IGNORE, "false");

        return defaults;
    }
}
