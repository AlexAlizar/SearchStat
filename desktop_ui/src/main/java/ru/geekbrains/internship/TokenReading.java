package ru.geekbrains.internship;

class TokenReading {

    public String readToken(String stringURL) throws Exception {
        String out = "";
        ConnectionDB connectionDB = new ConnectionDB(stringURL);
        out = connectionDB.readDBResult();
        connectionDB.closeConnectionDB();
        return out;
    }

}
