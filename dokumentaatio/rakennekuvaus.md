**Rakennekuvaus:**

Pacman -Peli tietää ja luo ikkunan, johon piirtäminen tapahtuu. Pelillä on myös tiedossa pelaaja, ja haamujen muokkaamiseen tarkoitettu avustava olio ghostHandler, joka tietää neljä haamua ja ohjaa niitä. Sekä pelaaja, että haamut perivät GameObject -luokan, joka tarjoaa objektien liikuttamiseen ja törmäämiseen metodeita, sekä pitää kirjaa peliobjektin sijainnista ja liikesuunnasta.

Peli -olio tuntee myös Level -kentän, jolla on taulukko, johon on tallennettu kentän osiin jakavat Tile -oliot. Level tarjoaa metodeita sekä kentän luomiseen kuvan avulla ja kaikkien Tile -olioiden tilan muuttamisen. Eri Tile oliot on joko syötäviä, seiniä, tai ei mitään, joiden sijainnit kenttä tietää.