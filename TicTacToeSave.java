class TicTacToeSave {
    private GererDB db;

    public TicTacToeSave() {
       // db = new GererDB();
    }

    public TicTacToeSave(GererDB db) {
        this.db = db;
    }

    public boolean CreateDatabase(String DBName){
        try {
            db.create(DBName);
            db.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveMove(Data data) {
        try {
            db.verify();
            db.save(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean initializeDB() {
        try {
            db.verify();
            db.drop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}