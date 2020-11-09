package de.lukaspanni.opensourcestats.data;


/**
 * Abstract base class for server response data
 */
public abstract class ResponseData {

    public enum QueryType{
        USER_CONTRIBUTIONS_QUERY,
        REPOSITORY_DATA_QUERY,
        NONE
    }

    private QueryType queryType;

    public ResponseData(QueryType queryType){
        this.queryType = queryType;
    }

    public QueryType getType(){
        return this.queryType;
    }
}
