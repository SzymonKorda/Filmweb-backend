INSERT INTO film (
  title, box_office, duration, description,
  release_year
)
VALUES
  (
    'Forrest Gump', 677387716, 144, 'Historia życia Forresta, chłopca o niskim ilorazie inteligencji z niedowładem kończyn, który staje się miliarderem i bohaterem wojny w Wietnamie.',
    1994
  ),
  (
    'Shawshank Redemption', 59841469,
    144, 'Adaptacja opowiadania Stephena Kinga. Niesłusznie skazany na dożywocie bankier, stara się przetrwać w brutalnym, więziennym świecie.',
    1994
  ),
  (
    'Zielona Mila', 286801374, 168, 'Emerytowany strażnik więzienny opowiada przyjaciółce o niezwykłym mężczyźnie, którego skazano na śmierć za zabójstwo dwóch 9-letnich dziewczynek.',
    1999
  ),
  (
    'Złap mnie, jeśli potrafisz',
    352114312, 141, 'Oparta na faktach historia młodego fałszerza, który w latach 60., podając się za pilotów, lekarzy i profesorów, wyłudził z banków ponad 2,5 mln dolarów.',
    2002
  ),
  (
    'Wyspa tajemnic', 294804195, 138,
    'Szeryf federalny Daniels stara się dowiedzieć, jakim sposobem z zamkniętej celi w pilnie strzeżonym szpitalu dla chorych psychicznie przestępców zniknęła pacjentka.',
    2010
  ),
  (
    'Nietykalni', 426588510, 172, 'Sparaliżowany milioner zatrudnia do opieki młodego chłopaka z przedmieścia, który właśnie wyszedł z więzienia.',
    2011
  ),
  (
    'Siedem', 327333559, 127, 'Dwóch policjantów stara się złapać seryjnego mordercę wybierającego swoje ofiary według specjalnego klucza - siedmiu grzechów głównych.',
    1995
  ),
  (
    'Szósty Zmysł', 672806292, 167,
    'Psycholog dziecięcy próbuje pomóc ośmioletniemu chłopcu widzącemu zmarłych poradzić sobie z tym niezwykłym darem.',
    1999
  ),
  (
    'Chłopcy z ferajny', 2108766, 146,
    'Kilkunastoletni Henry i Tommy DeVito trafiają pod opiekę potężnego gangstera. Obaj szybko uczą się panujących w mafii reguł.',
    1990
  ),
  (
    'Ojciec chrzestny', 245066411, 175,
    'Opowieść o nowojorskiej rodzinie mafijnej. Starzejący się Don Corleone pragnie przekazać władzę swojemu synowi.',
    1972
  ),
  (
    'Ojciec chrzestny II', 193000000,
    200, 'Rok 1917. Młody Vito Corleone stawia pierwsze kroki w mafijnym świecie Nowego Jorku. Ponad 40 lat później jego syn Michael walczy o interesy i dobro rodziny.',
    1974
  ),
  (
    'Zapach kobiety', 134095253, 157,
    'Niewidomy emerytowany pułkownik Frank Slade staje się najlepszym nauczycielem życia dla nieśmiałego studenta.',
    1992
  ),
  (
    'Lista Schindlera', 322139355, 195,
    'Historia przedsiębiorcy Oskara Schindlera, który podczas II wojny światowej uratował przed pobytem w obozach koncentracyjnych 1100 Żydów.',
    1993
  ),
  (
    'Co gryzie Gilberta Grape''a', 287595253,
    108, 'Gilbert wiedzie nudne życie zajmując się swoim chorym umysłowo bratem, dwiema siostrami i otyłą matką. Dopiero poznanie Becky odmieni jego życie.',
    1993
  ),
  (
    'Cast Away - poza światem', 429632142,
    143, 'W wyniku katastrofy lotniczej inżynier firmy kurierskiej trafia na bezludną wyspę, gdzie musi przeżyć.',
    2000
  ),
  (
    'Piękny umysł', 313542341, 135,
    'Geniusz matematyczny John Nash za wszelką cenę pragnie opracować teorię, dzięki której zostanie cenionym naukowcem. Przeszkodą staje się jego stopniowo rozwijająca choroba.',
    2001
  ) ON CONFLICT (title, release_year) DO NOTHING;

INSERT INTO actor (
  actor_id, first_name, last_name, height, description,
  born_year, born_place
)
VALUES
  (
    1, 'Tom', 'Hanks', 183, 'Jego rodzice rozwiedli się, gdy miał pięć lat. Wychowywał go ojciec – kucharz, który wkrótce przeniósł się z Kaliforni do Oakland. Aktorstwem Tom zainteresował się w szkole średniej.',
    1956, 'Concord, Kalifornia, USA'
  ),
  (
    2, 'Morgan', 'Freeman', 189, 'Jest jednym z najbardziej uznawanych aktorów w Hollywood, karierę zaczął od występów w musicalach.',
    1937, 'Memphis, Tennessee, USA'
  ),
  (
    3, 'Anthony', 'Hopkins', 174, 'Sir Philip Anthony Hopkins urodził się 31 grudnia 1937 roku w walijskim miasteczku Port Talbot. Jest jedynym dzieckiem Dicka i Muriel Hopkinsów.',
    1937, 'Port Talbot, Walia, Wielka Brytania'
  ),
  (
    4, 'Joaquin', 'Phoenix', 173, 'Brat legendarnego, zmarłego przedwcześnie aktora, Rivera Phoenixa i trójki innych artystów filmowych - Raina, Liberty i Summer. W dzieciństwie często podróżował wraz z rodziną, jako członek Kościoła',
    1974, 'San Juan, Portoryko'
  ),
  (
    5, 'Jack', 'Nicholson', 177, 'Będzie jedną z największych gwiazd filmowych wszystkich czasów" (New York Sunday Times, 6.04.1975). Tak mówił o przyszłości Jacka Nicholsona reżyser Mike Nichols. I nie mylił się.',
    1937, 'Neptune, New Jersey, USA'
  ),
  (
    6, 'Robert', 'Downey Jr.', 175, 'Robert John Downey Jr. urodził się w Greenwich Village, w Nowym Jorku, o godzinie 13:10. Jego przezwisko to Bob. Ma 175 cm wzrostu. Jest synem reżysera, Roberta Downeya Seniora.',
    1965, 'Nowy Jork, Nowy Jork, USA'
  ),
  (
    7, 'Alan', 'Rickman', 185, 'Alan Rickman karierę rozpoczynał w Anglii. Debiutował na scenie teatralnej, na której też odniósł swoje pierwsze sukcesy. Pod koniec lat 70. przyłączył się do Royal Shakespeare Company, z którą',
    1946, 'Londyn, Anglia, Wielka Brytania'
  ),
  (
    8, 'Al', 'Pacino', 170, 'Urodził się w rodzinie włoskich emigrantów w Nowym Jorku. Tam też uczęszczał do Wyższej Szkoły Aktorskiej, którą porzucił by przenieść się do Herbert Berghof Studio.',
    1940, 'Nowy Jork, Nowy Jork, USA'
  ),
  (
    9, 'Clint', 'Eastwood', 193, 'Clint Eastwood planował robić karierę sportową. Pasjonowała go lekkoatletyka, a aktorstwo traktował jako pracę dorywczą. Było tak do roku 1958, kiedy został zaangażowany jako',
    1930, 'San Francisco, Kalifornia, USA'
  ),
  (
    10, 'Benedict', 'Cumberbatch', 183, 'Benedict Cumberbatch urodził się w Londynie. Jest synem aktorów Timothy''ego Carltona (pełne imię Timothy Carlton Cumberbatch) i Wandy Ventham. Cumberbatch uczył się ',
    1976, 'Londyn, Anglia, Wielka Brytania'
  ),
  (
    11, 'Bryan', 'Cranston', 179, 'Amerykański aktor, aktor dubbingowy, a także reżyser i scenarzysta. Urodził się w Kalifornii w 1956 roku, jest synem aktora Josepha L. Cranstona i aktorki radiowej Peggy Sell. ',
    1956, 'Kalifornia, USA'
  ),
  (
    12, 'Kevin', 'Spacey', 179, 'Kevin Spacey, hollywoodzki złoty chłopiec, owiany jest mgiełką tajemnicy. Sam kiedyś przyznał: "nikt nie wie, kim jestem naprawdę i mam nadzieję, że ten stan rzeczy się utrzyma, gdyż jest ',
    1959, 'South Orange, New Jersey, USA'
  ),
  (
    13, 'Rami', 'Malek', 175, 'Rami Malek urodził się 12 maja 1981 roku w Los Angeles (Kalifornia, USA). Wraz z bratem Samim (młodszym o cztery minuty bliźniakiem jednojajowym) i starszą siostrą Yasmine wychowywał s',
    1981, 'Los Angeles, Kalifornia, USA'
  ),
  (
    14, 'Joe', 'Pesci', 163, 'W wieku 4 lat Joe Pesci po raz pierwszy wystąpił w radiu. Rok później zadebiutował na Broadwayu, a kiedy skończył 10 rok życia, miał już stałą pracę w programie telewizyjnym "Star Time Kids".',
    1943, 'Newark, New Jersey, USA'
  ),
  (
    15, 'Ian', 'McKellen', 180, 'Ian McKellen urodził się 25 maja 1939 r. w Burnley w Anglii. Skończył Uniwersytet w Cambridge na wydziale literatury angielskiej. Zadebiutował w 1961 roku w Belgrade Theatre w Coventry.',
    1939, 'Burnley, Anglia, Wielka Brytania'
  ),
  (
    16, 'Christoph', 'Waltz', 174, 'Urodził się 4 października 1956 roku w Wiedniu, gdzie uczęszczał na seminarium Maxa-Reinharda oraz Lee Strasberg Theater Institute w Nowym Jorku. ',
    1956, 'Wiedeń, Austria'
  )
ON CONFLICT (actor_id)
DO NOTHING;

INSERT INTO roles(id, name)
VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_ADMIN')
ON CONFLICT (id)
DO NOTHING;
