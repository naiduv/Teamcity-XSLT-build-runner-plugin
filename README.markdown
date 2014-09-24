Teamcity XSLT Build Runner plugin
====================================

This is forked from the Unity runner.
It simply runs XSLT Transforms using msxsl.exe, bundled with the code.


### Compilation

When building the plugin, you need to have downloaded the Teamcity distribution you want to build against, so that the compilation process can grab the necessary libs. It's also useful so that you can test that it will load the plugin correctly.

When running Teamcity locally, any plugins need to be installed by putting them in the TEAMCITY_DATA_PATH/plugins  folder.

You can find the  TEAMCITY_DATA_PATH is set under  Administration > Server Configuration > GlobalSetting ( according to this documentation: http://confluence.jetbrains.com/display/TCD4/TeamCity+Data+Directory )

On Nix and OSX this defaults to */home/<user>/.BuildServer*.

On Windows is by default:%PROGRAMDATA%\JetBrains\TeamCity ( Windows 8, Teamcity Version 8.0.4 )


Before compiling, it's important to update the build.properties file with the paths the Teamcity distribution and to the TEAMCITY_DATA_PATH folder. When the process is complete, ant should automatically copy the unityRunner.zip file to the Datapath folder.

#### Ant CLI

To compile with ant directly, just run *ant dist* from the root folder of the repo.

#### IntelliJ

Compile with [IntelliJ IDEA](http://www.jetbrains.com/idea/) -- the Community Edition (free) version will work!

Open up the unity_runner.ipr file, then open the Ant Build window and add build.xml (if not automatically detected).

Double click on 'dist' target to do a full build, which should produce dist/unityRunner.zip

### Installing in Teamcity

Copy the unityRunner.zip file to the TEAMCITY_DATA_PATH/plugins/ folder in your Teamcity installation, then restart Teamcity.


