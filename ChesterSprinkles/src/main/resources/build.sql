CREATE TABLE `users` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT, `first_name` TEXT NOT NULL, `last_name` TEXT NOT NULL, `slack_id` TEXT NOT NULL);

CREATE TABLE `challenges` (`challenge_id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `date_announced` TEXT NOT NULL, `presentation_date` TEXT NOT NULL);

CREATE TABLE `presentations` (`presentation_id` INTEGER PRIMARY KEY AUTOINCREMENT, `submitter_user_id` INTEGER NOT NULL, `challenge_id` INTEGER NOT NULL, FOREIGN KEY(`submitter_user_id`) REFERENCES `users`(`user_id`), FOREIGN KEY(`challenge_id`) REFERENCES `challenges`(`challenge_id`));

CREATE TABLE `pirate_ships` (`ship_id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `captain_user_id` INTEGER NOT NULL); --No clue what the difference is between ship points and overall ship points 

CREATE TABLE `pirates` (`pirate_id` INTEGER PRIMARY KEY AUTOINCREMENT, `user_id` INTEGER NOT NULL, `ship_id` INTEGER NOT NULL, `current_points` INTEGER, `total_points` INTEGER, `doubloons` INTEGER, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`), FOREIGN KEY(`ship_id`) REFERENCES `pirate_ships`(`ship_id`));

CREATE TABLE `challenge_ideas` (`ch_idea_id` INTEGER PRIMARY KEY AUTOINCREMENT, `user_id` TEXT NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`));

CREATE TABLE `command_ideas` (`co_idea_id` INTEGER PRIMARY KEY AUTOINCREMENT, `user_id` TEXT NOT NULL, `command` TEXT NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`));

CREATE TABLE `parrot_phrases` (`phrase_id` INTEGER PRIMARY KEY AUTOINCREMENT, `phrase` TEXT NOT NULL);

