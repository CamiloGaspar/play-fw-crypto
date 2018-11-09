package util;

import java.util.Properties;

public class ConfiguracionPropertiesLoader {
    private static ConfiguracionPropertiesLoader descPropsLoader = null;

    private static final String RUTA_DESCRIPCION = "conf/acti-application.properties";

    private Properties properties = null;


    /**
     * M&eacute;todo constructor de la clase.
     * @throws Exception
     */
    private ConfiguracionPropertiesLoader() throws Exception {
        loadPropertiesFile();
    }

    /**
     * M&eacute;todo encargado de devolver una nueva instancia de la clase.
     * @return
     * @throws Exception
     */
    public static ConfiguracionPropertiesLoader getInstance() throws Exception {
        if (descPropsLoader == null) {
            descPropsLoader = new ConfiguracionPropertiesLoader();
        }

        return descPropsLoader;
    }



    /**
     * M&eacute;todo que se encarga de retornar una determinada propiedad.
     * @param prop
     * @return
     */
    public  String getValorPropiedad(String prop) {
        try {
            return getInstance().properties.getProperty(prop);
        } catch (Exception e) {
            //            logger.error("Error cargando propiedad " + prop + ": " + e.getMessage(), e);
            return null;
        }
    }



    /**
     * M&eacute;todo que se encarga de leer un archivo de propiedades.
     * @throws Exception
     */
    private void loadPropertiesFile() throws Exception {
        try {
            //            logger.debug("errorPath: " + errorsPath);
            //            logger.debug("propsPath: " + propsPath);

            if (RUTA_DESCRIPCION != null && !RUTA_DESCRIPCION.trim().equals("")) {

                Properties props = new Properties();


                props.load(ConfiguracionPropertiesLoader.class.getClassLoader().getResourceAsStream("acti-application.properties"));
                properties = props;

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            //            logger.error("Error cargando archivos de propiedades");

            throw new Exception("Error cargando el archivo de propiedades.",e);
        }
    }
}
