# PortluxPocket

API-länkar:
api/user/get/{userid}[/(xml|json)]<br>
api/user/list[/(xml|json)]<br>
api/berth/get/{berthid}[/(xml|json)]<br>
api/berth/list[/(xml|json)]<br>
api/ticket/get/{ticketid}[/(xml|json)]<br>
api/ticket/list[/(xml|json)]<br>

test:<br>
http://test.palholmen.se/api/user/get/2/xml<br>
http://test.palholmen.se/api/user/list/xml<br>

Sökningen bör gå igenom fältet 'name' i användaren och fältet 'berth' i båtplatsen
'name' är en sammanslagning av 'firstName', 'middleName', 'lastName' och 'orgName'
'berth' är en sammanslagning av 'pier' och 'name'<br>

'free' i kontraktet markerar om en användare har markerat att han vill hyra ut eller inte och 'vacant' markerar om den ska säljas<br>

"Jag vill hyra plats i år" beskrivs i köbiljetten med hjälp av <contractTypes> (eg. biljetten är intresserad utav kontrakt av typen 'contractTypes'), dock är detta föremål till förändring men vi kan nog inte vänta på det. 
"Controls" är en länk mellan en nyttjanderätt och en hyresrätt och är oftast intressant när kunden har mer än en båtplats

Skapa api-nyckel genom att skicka 'email' och 'password' som post-data till api.portlux.se/public/createkey/{json | xml}
Du kommer få tillbaka ett svar med 'url' och 'key', 'url' är då hamnen (eg. kunden).

Använd: test@portlux.se och FUuRV2Pv som epost respektive lösenord.
'url' som du får tillbaka ska vara test.portlux.se, får du något annat så hör av dig.

När du gör anrop mot apiet så ska du inkludera apinyckeln i varje request som get-data 'apikey' (eg .../api/user/list/xml?apikey='key').

För att markera en plats ledig (hyra ut) så skickar du kontraktet som post-data till /api/contract/set/{json | xml} med 'free' parametern true (alt. false om du ska ta tillbaka den). 
Svaret är det uppdaterade kontraktet.

Elkontraktet ser ut så här:
<powerContracts>
<id>3</id>
<name>El Sommar</name>
<berthid>3</berthid>
<berth>A3</berth>
<userid>9</userid>
<user>Per Åhnebrink</user>
<start>2015-06-01</start>
<end>2015-09-30</end>
<controls>2015-06-22</controls>
<controls>2015-07-30</controls>
</powerContracts>

För att uppdatera ett el-kontrakt så skickar du kontraktet som post-data till /api/powercontract/set/{json | xml}. 
Om du inte har med 'id' så antar den att den ska skapa ett nytt kontrakt, viktigt då att du har med 'userid' och 'berthid' för att kunna koppla kontraktet resten plockas fram själv.
Om du vill lägga till en markering så behöver du bara utöka 'controls' listan med dagens datum.
Svar blir det nya/uppdaterade kontraktet.
