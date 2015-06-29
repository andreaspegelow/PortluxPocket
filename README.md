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
