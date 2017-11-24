    CREATE TABLE Vinkki (
        vinkki_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        otsikko TEXT NOT NULL,
        kuvaus TEXT,
        tyyppi TEXT NOT NULL DEFAULT 'kirja'
    );


    CREATE TABLE Tag (
        tag_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        tag TEXT NOT NULL
    );


    CREATE TABLE VinkkiTag (
        vinkki INTEGER NOT NULL,
        tag INTEGER NOT NULL,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id),
        FOREIGN KEY(tag) REFERENCES Tag(tag_id)
    );


    CREATE TABLE Kirja (
        vinkki INTEGER NOT NULL,
        ISBN TEXT,
        kirjailija TEXT,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id)
    );
