use runner_db;

CREATE TABLE `runners` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `login` varchar(45) NOT NULL,
    `password` varchar(75) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `runners_races` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `distance` double NOT NULL,
    `race_time` time NOT NULL,
    `race_date` date NOT NULL,
    `speed` double NOT NULL,
    `race_duration` time(3) NOT NULL,
    `runner_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `INDEX_race_date` (`race_date`) /*!80000 INVISIBLE */,
    KEY `FK_runners_races_idx` (`runner_id`),
    CONSTRAINT `FK_runner_id` FOREIGN KEY (`runner_id`) REFERENCES `runners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;