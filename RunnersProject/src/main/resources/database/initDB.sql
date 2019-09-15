CREATE TABLE IF NOT EXISTS runners_races {
    id int primary key,
    distance double not null,
    rece_time time not null,
    race_date date not null,
    constraint 'FK_runners_races_id' foreign key ('id') references 'runners' ('id') on delete cascade on update cascade
} ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS runners_races_info {
    id int primary key,
    speed double,
    race_duration_time time,
    constraint 'FK_runners_races_info_id' foreign key ('id') references 'runners_races' ('id') on delete cascade on update cascade
}