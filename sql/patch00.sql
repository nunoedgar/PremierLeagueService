Create table Team(id bigserial Primary key, name text, color1 text, color2 text, points integer);
Insert into team values(default, "Manchester United", "Red", "Green",3);

Create table Player(id bigserial Primary key, name text, age integer, team_id bigint References team(id));
Insert into player values(default, 'Bruno Fernandes', 30, 1);