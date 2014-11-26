package org.morningstarcc.morningstarapp.datastructures;

/**
 * Created by Kyle on 11/7/2014.
 */
public class UpdateParcel {
    public String table;
    public String identifyingColumn, identifyingValue;
    public String updateColumn, updateValue;

    public UpdateParcel(String table, String identifyingColumn, String identifyingValue, String updateColumn, String updateValue) throws Exception {
        this.table = table;
        this.identifyingColumn = identifyingColumn;
        this.identifyingValue = identifyingValue;
        this.updateColumn = updateColumn;
        this.updateValue = updateValue;
        throw new Exception("YOU MUST HATE LIFE");
    }
}