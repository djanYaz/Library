PGDMP     /                
    y         	   libraryDB    14.0    14.0                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            !           1262    16394 	   libraryDB    DATABASE     l   CREATE DATABASE "libraryDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Bulgarian_Bulgaria.1251';
    DROP DATABASE "libraryDB";
                postgres    false            ?            1259    16395    book    TABLE     ?   CREATE TABLE public.book (
    id integer NOT NULL,
    title character varying(70),
    author character varying(70),
    year_published integer,
    genre_id integer
);
    DROP TABLE public.book;
       public         heap    postgres    false            ?            1259    16400    genre    TABLE     Y   CREATE TABLE public.genre (
    id integer NOT NULL,
    genre_type character varying
);
    DROP TABLE public.genre;
       public         heap    postgres    false            ?            1259    16412    stock    TABLE     a   CREATE TABLE public.stock (
    id integer NOT NULL,
    book_id integer,
    numbers integer
);
    DROP TABLE public.stock;
       public         heap    postgres    false            ?            1259    16457    book_inspection_view    VIEW       CREATE VIEW public.book_inspection_view AS
 SELECT a.title AS book_title,
    a.author AS author_name,
    b.genre_type,
    c.numbers AS pieces
   FROM ((public.book a
     LEFT JOIN public.genre b ON ((a.genre_id = b.id)))
     LEFT JOIN public.stock c ON ((a.id = c.book_id)));
 '   DROP VIEW public.book_inspection_view;
       public          postgres    false    209    209    209    210    210    211    209    211            ?            1259    16427    borrowedbook    TABLE     ?   CREATE TABLE public.borrowedbook (
    id integer NOT NULL,
    book_id integer,
    reader_id integer,
    borrow_date date,
    return_date date
);
     DROP TABLE public.borrowedbook;
       public         heap    postgres    false            ?            1259    16442 
   outofstock    TABLE        CREATE TABLE public.outofstock (
    id integer NOT NULL,
    reader_id integer,
    book_id integer,
    date_created date
);
    DROP TABLE public.outofstock;
       public         heap    postgres    false            ?            1259    16422    reader    TABLE        CREATE TABLE public.reader (
    id integer NOT NULL,
    first_name character varying(20),
    last_name character varying(20),
    city character varying(30),
    phone character varying(10),
    email character varying(40),
    borrow_number integer
);
    DROP TABLE public.reader;
       public         heap    postgres    false            ?            1259    16461    reader_borrow_view    VIEW     W  CREATE VIEW public.reader_borrow_view AS
 SELECT a.first_name AS reader_first_name,
    a.last_name AS reader_last_name,
    a.phone,
    b.title AS book_title,
    c.borrow_date,
    c.return_date
   FROM ((public.borrowedbook c
     LEFT JOIN public.book b ON ((c.book_id = b.id)))
     LEFT JOIN public.reader a ON ((a.id = c.reader_id)));
 %   DROP VIEW public.reader_borrow_view;
       public          postgres    false    209    213    213    213    213    212    212    212    212    209                      0    16395    book 
   TABLE DATA           K   COPY public.book (id, title, author, year_published, genre_id) FROM stdin;
    public          postgres    false    209   ?#                 0    16427    borrowedbook 
   TABLE DATA           X   COPY public.borrowedbook (id, book_id, reader_id, borrow_date, return_date) FROM stdin;
    public          postgres    false    213   ?$                 0    16400    genre 
   TABLE DATA           /   COPY public.genre (id, genre_type) FROM stdin;
    public          postgres    false    210   *%                 0    16442 
   outofstock 
   TABLE DATA           J   COPY public.outofstock (id, reader_id, book_id, date_created) FROM stdin;
    public          postgres    false    214   ?%                 0    16422    reader 
   TABLE DATA           ^   COPY public.reader (id, first_name, last_name, city, phone, email, borrow_number) FROM stdin;
    public          postgres    false    212   ?%                 0    16412    stock 
   TABLE DATA           5   COPY public.stock (id, book_id, numbers) FROM stdin;
    public          postgres    false    211   ?&       x           2606    16399    book book_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
       public            postgres    false    209            ?           2606    16431    borrowedbook borrowedbook_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.borrowedbook
    ADD CONSTRAINT borrowedbook_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.borrowedbook DROP CONSTRAINT borrowedbook_pkey;
       public            postgres    false    213            z           2606    16406    genre genre_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.genre DROP CONSTRAINT genre_pkey;
       public            postgres    false    210            ?           2606    16446    outofstock outofstock_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.outofstock
    ADD CONSTRAINT outofstock_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.outofstock DROP CONSTRAINT outofstock_pkey;
       public            postgres    false    214            ~           2606    16426    reader reader_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.reader DROP CONSTRAINT reader_pkey;
       public            postgres    false    212            |           2606    16416    stock stock_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.stock DROP CONSTRAINT stock_pkey;
       public            postgres    false    211            ?           2606    16407    book book_genre_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES public.genre(id) NOT VALID;
 A   ALTER TABLE ONLY public.book DROP CONSTRAINT book_genre_id_fkey;
       public          postgres    false    210    209    3194            ?           2606    16432 &   borrowedbook borrowedbook_book_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.borrowedbook
    ADD CONSTRAINT borrowedbook_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.book(id);
 P   ALTER TABLE ONLY public.borrowedbook DROP CONSTRAINT borrowedbook_book_id_fkey;
       public          postgres    false    3192    213    209            ?           2606    16437 (   borrowedbook borrowedbook_reader_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.borrowedbook
    ADD CONSTRAINT borrowedbook_reader_id_fkey FOREIGN KEY (reader_id) REFERENCES public.reader(id);
 R   ALTER TABLE ONLY public.borrowedbook DROP CONSTRAINT borrowedbook_reader_id_fkey;
       public          postgres    false    213    212    3198            ?           2606    16452 "   outofstock outofstock_book_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.outofstock
    ADD CONSTRAINT outofstock_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.book(id);
 L   ALTER TABLE ONLY public.outofstock DROP CONSTRAINT outofstock_book_id_fkey;
       public          postgres    false    3192    214    209            ?           2606    16447 $   outofstock outofstock_reader_id_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public.outofstock
    ADD CONSTRAINT outofstock_reader_id_fkey FOREIGN KEY (reader_id) REFERENCES public.reader(id);
 N   ALTER TABLE ONLY public.outofstock DROP CONSTRAINT outofstock_reader_id_fkey;
       public          postgres    false    214    3198    212            ?           2606    16417    stock stock_book_id_fkey    FK CONSTRAINT     v   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.book(id);
 B   ALTER TABLE ONLY public.stock DROP CONSTRAINT stock_book_id_fkey;
       public          postgres    false    3192    211    209               ?   x?-OAn?@;Ͼb_??"?/y??Q???z???	??MZ?<???r@??ۓ???{$?1i?I??"F?7?铄,??@?V??f???9?ǝ??????c?gDAG?'6??+?>????G)\!??V??[?I?w?]Y(Y?=Ǩ[n?LJ?6:R???e{??#͖O??̎2E???c???=Љ?/̭t?8?ڲ??e?'??vo??jV??&??6+??x???         3   x?-??	  ?w?K??"????a?????R?n?HcB?????"? ? 12	?         f   x?=??	?@D?I1??j,FE??®n?????!?^->!?B????Z_|f"???ϼ?a?	?]e*?7?_?bA?n
?t???k?JU??T`            x?????? ? ?         .  x?e??J?0??ׇ?M????????0??-??*??EA???ʺ???Ѿd?;䐐?????7??>??;?q?{??\.????g8SZ+???0?L??y>?F?0??b?7?a?-?'?omM?v??v??h?{G0	E~g??<p??<nq?ꍆ?-????<??"U1???oL?? ?>??U?<?K?g?H$O??????z?>7}RK?vnӧ9i&Ob%`|m??4?????WN?X???ZQ??jZV??~?B5?7?R/Ա??}"҅???5y&??&????U??AEPh??         (   x??I 0İw??æ?q????,?vⲌ!???Rc     