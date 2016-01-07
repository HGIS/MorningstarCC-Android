package org.morningstarcc.app.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by juanmanuelgomezllanos on 12/2/15.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil{

    public static void main(String[] args) throws Exception{
        writeConfigFile("ormlite_config.txt");
    }
}
