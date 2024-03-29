--
-- PostgreSQL database dump
--

-- Dumped from database version 11.13
-- Dumped by pg_dump version 11.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actor (
    actor_id bigint NOT NULL,
    born_place character varying(255),
    born_year integer NOT NULL,
    description character varying(255),
    first_name character varying(255),
    height integer NOT NULL,
    last_name character varying(255)
);


ALTER TABLE public.actor OWNER TO postgres;

--
-- Name: actor_actor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.actor ALTER COLUMN actor_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.actor_actor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    comment_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    content character varying(255),
    film_id bigint,
    user_id bigint
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- Name: comment_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.comment ALTER COLUMN comment_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.comment_comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: film; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.film (
    film_id bigint NOT NULL,
    box_office integer NOT NULL,
    description character varying(255),
    duration integer NOT NULL,
    release_year integer NOT NULL,
    title character varying(255)
);


ALTER TABLE public.film OWNER TO postgres;

--
-- Name: film_actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.film_actor (
    film_id bigint NOT NULL,
    actor_id bigint NOT NULL
);


ALTER TABLE public.film_actor OWNER TO postgres;

--
-- Name: film_film_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.film ALTER COLUMN film_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.film_film_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(60)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.roles ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_films; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_films (
    user_id bigint NOT NULL,
    film_id bigint NOT NULL
);


ALTER TABLE public.user_films OWNER TO postgres;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    email character varying(40),
    name character varying(40),
    password character varying(100),
    username character varying(40)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actor (actor_id, born_place, born_year, description, first_name, height, last_name) FROM stdin;
1	Concord, Kalifornia, USA	1956	Jego rodzice rozwiedli się, gdy miał pięć lat. Wychowywał go ojciec – kucharz, który wkrótce przeniósł się z Kaliforni do Oakland. Aktorstwem Tom zainteresował się w szkole średniej.	Tom	183	Hanks
2	Memphis, Tennessee, USA	1937	Jest jednym z najbardziej uznawanych aktorów w Hollywood, karierę zaczął od występów w musicalach.	Morgan	189	Freeman
3	Port Talbot, Walia, Wielka Brytania	1937	Sir Philip Anthony Hopkins urodził się 31 grudnia 1937 roku w walijskim miasteczku Port Talbot. Jest jedynym dzieckiem Dicka i Muriel Hopkinsów.	Anthony	174	Hopkins
4	San Juan, Portoryko	1974	Brat legendarnego, zmarłego przedwcześnie aktora, Rivera Phoenixa i trójki innych artystów filmowych - Raina, Liberty i Summer. W dzieciństwie często podróżował wraz z rodziną, jako członek Kościoła	Joaquin	173	Phoenix
5	Neptune, New Jersey, USA	1937	Będzie jedną z największych gwiazd filmowych wszystkich czasów" (New York Sunday Times, 6.04.1975). Tak mówił o przyszłości Jacka Nicholsona reżyser Mike Nichols. I nie mylił się.	Jack	177	Nicholson
6	Nowy Jork, Nowy Jork, USA	1965	Robert John Downey Jr. urodził się w Greenwich Village, w Nowym Jorku, o godzinie 13:10. Jego przezwisko to Bob. Ma 175 cm wzrostu. Jest synem reżysera, Roberta Downeya Seniora.	Robert	175	Downey Jr.
7	Londyn, Anglia, Wielka Brytania	1946	Alan Rickman karierę rozpoczynał w Anglii. Debiutował na scenie teatralnej, na której też odniósł swoje pierwsze sukcesy. Pod koniec lat 70. przyłączył się do Royal Shakespeare Company, z którą	Alan	185	Rickman
8	Nowy Jork, Nowy Jork, USA	1940	Urodził się w rodzinie włoskich emigrantów w Nowym Jorku. Tam też uczęszczał do Wyższej Szkoły Aktorskiej, którą porzucił by przenieść się do Herbert Berghof Studio.	Al	170	Pacino
9	San Francisco, Kalifornia, USA	1930	Clint Eastwood planował robić karierę sportową. Pasjonowała go lekkoatletyka, a aktorstwo traktował jako pracę dorywczą. Było tak do roku 1958, kiedy został zaangażowany jako	Clint	193	Eastwood
10	Londyn, Anglia, Wielka Brytania	1976	Benedict Cumberbatch urodził się w Londynie. Jest synem aktorów Timothy'ego Carltona (pełne imię Timothy Carlton Cumberbatch) i Wandy Ventham. Cumberbatch uczył się 	Benedict	183	Cumberbatch
11	Kalifornia, USA	1956	Amerykański aktor, aktor dubbingowy, a także reżyser i scenarzysta. Urodził się w Kalifornii w 1956 roku, jest synem aktora Josepha L. Cranstona i aktorki radiowej Peggy Sell. 	Bryan	179	Cranston
12	South Orange, New Jersey, USA	1959	Kevin Spacey, hollywoodzki złoty chłopiec, owiany jest mgiełką tajemnicy. Sam kiedyś przyznał: "nikt nie wie, kim jestem naprawdę i mam nadzieję, że ten stan rzeczy się utrzyma, gdyż jest 	Kevin	179	Spacey
13	Los Angeles, Kalifornia, USA	1981	Rami Malek urodził się 12 maja 1981 roku w Los Angeles (Kalifornia, USA). Wraz z bratem Samim (młodszym o cztery minuty bliźniakiem jednojajowym) i starszą siostrą Yasmine wychowywał s	Rami	175	Malek
14	Newark, New Jersey, USA	1943	W wieku 4 lat Joe Pesci po raz pierwszy wystąpił w radiu. Rok później zadebiutował na Broadwayu, a kiedy skończył 10 rok życia, miał już stałą pracę w programie telewizyjnym "Star Time Kids".	Joe	163	Pesci
15	Burnley, Anglia, Wielka Brytania	1939	Ian McKellen urodził się 25 maja 1939 r. w Burnley w Anglii. Skończył Uniwersytet w Cambridge na wydziale literatury angielskiej. Zadebiutował w 1961 roku w Belgrade Theatre w Coventry.	Ian	180	McKellen
16	Wiedeń, Austria	1956	Urodził się 4 października 1956 roku w Wiedniu, gdzie uczęszczał na seminarium Maxa-Reinharda oraz Lee Strasberg Theater Institute w Nowym Jorku. 	Christoph	174	Waltz
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comment (comment_id, created_at, updated_at, content, film_id, user_id) FROM stdin;
\.


--
-- Data for Name: film; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.film (film_id, box_office, description, duration, release_year, title) FROM stdin;
1	677387716	Historia życia Forresta, chłopca o niskim ilorazie inteligencji z niedowładem kończyn, który staje się miliarderem i bohaterem wojny w Wietnamie.	144	1994	Forrest Gump
2	59841469	Adaptacja opowiadania Stephena Kinga. Niesłusznie skazany na dożywocie bankier, stara się przetrwać w brutalnym, więziennym świecie.	144	1994	Shawshank Redemption
3	286801374	Emerytowany strażnik więzienny opowiada przyjaciółce o niezwykłym mężczyźnie, którego skazano na śmierć za zabójstwo dwóch 9-letnich dziewczynek.	168	1999	Zielona Mila
4	352114312	Oparta na faktach historia młodego fałszerza, który w latach 60., podając się za pilotów, lekarzy i profesorów, wyłudził z banków ponad 2,5 mln dolarów.	141	2002	Złap mnie, jeśli potrafisz
5	294804195	Szeryf federalny Daniels stara się dowiedzieć, jakim sposobem z zamkniętej celi w pilnie strzeżonym szpitalu dla chorych psychicznie przestępców zniknęła pacjentka.	138	2010	Wyspa tajemnic
6	426588510	Sparaliżowany milioner zatrudnia do opieki młodego chłopaka z przedmieścia, który właśnie wyszedł z więzienia.	172	2011	Nietykalni
7	327333559	Dwóch policjantów stara się złapać seryjnego mordercę wybierającego swoje ofiary według specjalnego klucza - siedmiu grzechów głównych.	127	1995	Siedem
8	672806292	Psycholog dziecięcy próbuje pomóc ośmioletniemu chłopcu widzącemu zmarłych poradzić sobie z tym niezwykłym darem.	167	1999	Szósty Zmysł
9	2108766	Kilkunastoletni Henry i Tommy DeVito trafiają pod opiekę potężnego gangstera. Obaj szybko uczą się panujących w mafii reguł.	146	1990	Chłopcy z ferajny
10	245066411	Opowieść o nowojorskiej rodzinie mafijnej. Starzejący się Don Corleone pragnie przekazać władzę swojemu synowi.	175	1972	Ojciec chrzestny
11	193000000	Rok 1917. Młody Vito Corleone stawia pierwsze kroki w mafijnym świecie Nowego Jorku. Ponad 40 lat później jego syn Michael walczy o interesy i dobro rodziny.	200	1974	Ojciec chrzestny II
12	134095253	Niewidomy emerytowany pułkownik Frank Slade staje się najlepszym nauczycielem życia dla nieśmiałego studenta.	157	1992	Zapach kobiety
13	322139355	Historia przedsiębiorcy Oskara Schindlera, który podczas II wojny światowej uratował przed pobytem w obozach koncentracyjnych 1100 Żydów.	195	1993	Lista Schindlera
14	287595253	Gilbert wiedzie nudne życie zajmując się swoim chorym umysłowo bratem, dwiema siostrami i otyłą matką. Dopiero poznanie Becky odmieni jego życie.	108	1993	Co gryzie Gilberta Grape'a
15	429632142	W wyniku katastrofy lotniczej inżynier firmy kurierskiej trafia na bezludną wyspę, gdzie musi przeżyć.	143	2000	Cast Away - poza światem
16	313542341	Geniusz matematyczny John Nash za wszelką cenę pragnie opracować teorię, dzięki której zostanie cenionym naukowcem. Przeszkodą staje się jego stopniowo rozwijająca choroba.	135	2001	Test8
\.


--
-- Data for Name: film_actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.film_actor (film_id, actor_id) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- Data for Name: user_films; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_films (user_id, film_id) FROM stdin;
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, updated_at, email, name, password, username) FROM stdin;
\.


--
-- Name: actor_actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.actor_actor_id_seq', 16, true);


--
-- Name: comment_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comment_comment_id_seq', 1, false);


--
-- Name: film_film_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.film_film_id_seq', 24, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: actor actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor
    ADD CONSTRAINT actor_pkey PRIMARY KEY (actor_id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (comment_id);


--
-- Name: film_actor film_actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film_actor
    ADD CONSTRAINT film_actor_pkey PRIMARY KEY (film_id, actor_id);


--
-- Name: film film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film
    ADD CONSTRAINT film_pkey PRIMARY KEY (film_id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: film uknkijms713kxl22hqn0i9f6qs9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film
    ADD CONSTRAINT uknkijms713kxl22hqn0i9f6qs9 UNIQUE (title, release_year);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_films user_films_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_films
    ADD CONSTRAINT user_films_pkey PRIMARY KEY (user_id, film_id);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: film_actor fk44uk58x166xx1qd03300206nr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film_actor
    ADD CONSTRAINT fk44uk58x166xx1qd03300206nr FOREIGN KEY (film_id) REFERENCES public.film(film_id);


--
-- Name: comment fkb6gnv47yxa2jewd4jpvm3pnfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkb6gnv47yxa2jewd4jpvm3pnfk FOREIGN KEY (film_id) REFERENCES public.film(film_id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_films fkip31jnt0bdpgukkryan94j67q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_films
    ADD CONSTRAINT fkip31jnt0bdpgukkryan94j67q FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: comment fkqm52p1v3o13hy268he0wcngr5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkqm52p1v3o13hy268he0wcngr5 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: film_actor fksr7lo9p4intei645cws4f9t4l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.film_actor
    ADD CONSTRAINT fksr7lo9p4intei645cws4f9t4l FOREIGN KEY (actor_id) REFERENCES public.actor(actor_id) ON DELETE CASCADE;


--
-- Name: user_films fkssbavyjvf08o7rvwewxeatfv9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_films
    ADD CONSTRAINT fkssbavyjvf08o7rvwewxeatfv9 FOREIGN KEY (film_id) REFERENCES public.film(film_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

