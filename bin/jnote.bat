@echo off
set cur_dir=%CD%
cd %JNOTE_HOME%
java -classpath  "%JNOTE_HOME%;sqlite-jdbc-3.27.2.1.jar" Jnote %1

cd /d %cur_dir%