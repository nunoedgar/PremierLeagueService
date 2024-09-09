Create table Team(id bigserial Primary key, name text, color1 text, color2 text, points integer);
Insert into team values(1, 'Manchester United', 'Red', 'Green',3);
Insert into team values(2, 'Liverpool', 'Red', 'White',2);
Insert into team values(3, 'Manchester City', 'Blue', 'Yellow',6);

Create table Player(id bigserial Primary key, name text, age integer, team_id bigint References team(id));
Insert into player values(default, 'Bruno Fernandes', 30, 1);
Insert into player values(default, 'Manuel Ugarte', 23, 1);
Insert into player values(default, 'Diogo Jota', 27, 2);
Insert into player values(default, 'Mohamed Salah', 32, 2);
Insert into player values(default, 'Kevin de Bruyne', 33, 3);
Insert into player values(default, 'Phil Foden', 24, 3);
