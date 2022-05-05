This is a folder with shared libraries (not sensors) for each provider type.
Files are named as {provider type}.sql.jinja2
Those files should contain macros that are rendering provider specific SQL strings like a properly quoted table or column name.
Files in this folder should be referenced from the templates (also from the templates in the user home folder, the Jinja2 template loader will handle loading those files from the DQO_HOME).

