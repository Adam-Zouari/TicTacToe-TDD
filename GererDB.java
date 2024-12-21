interface GererDB {
    void setDbName(String DBName) throws Exception;
    void connect() throws Exception;
    void verify() throws Exception;
    void create(String DbName) throws Exception;
    void drop() throws Exception;
    void save(Data data) throws Exception;
}