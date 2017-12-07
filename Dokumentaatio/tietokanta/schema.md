    CREATE TABLE Vinkki (
        vinkki_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        otsikko TEXT NOT NULL,
        kuvaus TEXT,
        tyyppi TEXT NOT NULL DEFAULT 'kirja'
    );

    
    CREATE TABLE Tag (
        tag_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        tag TEXT NOT NULL
        tyyppi TEXT NOT NULL DEFAULT "tag"
    );
    
    CREATE TABLE Kirja (
        vinkki INTEGER NOT NULL,
        ISBN TEXT,
        kirjailija TEXT,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id) ON DELETE CASCADE
    );

    
    CREATE TABLE VinkkiTag (
        vinkki INTEGER NOT NULL,
        tag INTEGER NOT NULL,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id) ON DELETE CASCADE,
        FOREIGN KEY(tag) REFERENCES Tag(tag_id) ON DELETE CASCADE
    );

    
    CREATE TRIGGER poista_turha_tag
        AFTER DELETE 
        ON VinkkiTag
        BEGIN
            DELETE FROM Tag WHERE Tag.tag_id IN (SELECT Tag.tag_id FROM Tag LEFT JOIN VinkkiTag ON Tag.tag_id = VinkkiTag.tag WHERE VinkkiTag.tag IS NULL);
        END;
    
    CREATE TABLE Video (
        vinkki INTEGER NOT NULL,
        tekija TEXT,
        url TEXT NOT NULL,
        pvm TEXT,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id) ON DELETE CASCADE
    );

    
    CREATE TABLE Blogi (
        vinkki INTEGER NOT NULL,
        kirjoittaja TEXT,
        nimi TEXT,
        url TEXT NOT NULL,
        pvm TEXT,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id) ON DELETE CASCADE
    );

    
    CREATE TABLE Podcast (
        vinkki INTEGER NOT NULL,
        tekija TEXT,
        nimi TEXT,
        url TEXT NOT NULL,
        pvm TEXT,
        FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id) ON DELETE CASCADE
    );
