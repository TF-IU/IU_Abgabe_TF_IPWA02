-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Aug 2025 um 13:06
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `require4testing`
--
CREATE DATABASE IF NOT EXISTS `require4testing` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `require4testing`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `requirement`
--

CREATE TABLE `requirement` (
  `created_by_user_ref` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `description` varchar(8000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `requirement`
--

INSERT INTO `requirement` (`created_by_user_ref`, `id`, `description`, `title`, `createdAt`) VALUES
(1, 7, 'Beschreibung', 'Testanforderung 1', '2025-07-26 13:03:21.000000'),
(1, 8, 'Beschreibung 2', 'Testanforderung 2', '2025-07-26 13:41:32.000000'),
(1, 9, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'Testanforderung 3', '2025-07-26 18:18:35.000000');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `requirement_seq`
--

CREATE TABLE `requirement_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `requirement_seq`
--

INSERT INTO `requirement_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testcase`
--

CREATE TABLE `testcase` (
  `created_by_user_ref` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `requirement_ref` int(11) DEFAULT NULL,
  `description` varchar(8000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `result` enum('NOT_TESTED','IN_PROCESS','FAILED','PASSED') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `testcase`
--

INSERT INTO `testcase` (`created_by_user_ref`, `id`, `requirement_ref`, `description`, `title`, `result`) VALUES
(1, 1, 7, 'Beschreibung 1', 'Testfall 1', 'IN_PROCESS'),
(1, 3, 8, 'Beschreibung 2', 'Testfall 2', 'NOT_TESTED'),
(1, 4, 9, 'Beschreibung 3', 'Testfall 3', 'NOT_TESTED');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testcase_seq`
--

CREATE TABLE `testcase_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `testcase_seq`
--

INSERT INTO `testcase_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testrun`
--

CREATE TABLE `testrun` (
  `assigned_to_user_ref` int(11) DEFAULT NULL,
  `created_by_user_ref` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `testrun`
--

INSERT INTO `testrun` (`assigned_to_user_ref`, `created_by_user_ref`, `id`, `title`) VALUES
(1, 1, 2, 'Testlauf 1'),
(3, 1, 5, 'Testlauf 2'),
(NULL, 1, 6, 'Testlauf 3');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testrun_seq`
--

CREATE TABLE `testrun_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `testrun_seq`
--

INSERT INTO `testrun_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testrun_testcase`
--

CREATE TABLE `testrun_testcase` (
  `TestCase_ref` int(11) NOT NULL,
  `TestRun_ref` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `testrun_testcase`
--

INSERT INTO `testrun_testcase` (`TestCase_ref`, `TestRun_ref`) VALUES
(3, 5),
(4, 5),
(1, 2),
(3, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `displayName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`id`, `name`, `password`, `displayName`) VALUES
(1, 'Admin', '$2a$10$/YgBhqifmJ8FNhy4tyRIg.HnIx5pKYBGdqWq9y1x/ygB.0K9h0f42', 'Administrator'),
(3, 'Tester', '$2a$10$NvGzhy9KzkIrdVH/JDdegeHdHchxry7XpEmFjG75j513eRpdW9zuK', 'Tina Tester'),
(4, 'Manager', '$2a$10$rt/hL2UJRQlBYjKV9l7XQuwcC2GwCL8hQLuvdbL1dYJp7NV8urU3q', 'Markus Manager'),
(5, 'Fallersteller', '$2a$10$iejyx1qQEIL4WNH11jdrseWy.lR9M38VkP0ZtKOPMIZ4Vzaw1WaNi', 'Fabian Fallersteller'),
(6, 'RE', '$2a$10$cWsHFg6JF713Y4tJ4uClAeCjmF9Wcms7dHNiRTYvDMciuS.e6EO9y', 'Renate R. Engineer');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_roles`
--

CREATE TABLE `user_roles` (
  `User_id` int(11) NOT NULL,
  `roles` enum('TESTER','TESTMANAGER','TESTFALLERSTELLER','REQUIREMENTS_ENGINEER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `user_roles`
--

INSERT INTO `user_roles` (`User_id`, `roles`) VALUES
(1, 'TESTER'),
(1, 'TESTMANAGER'),
(1, 'TESTFALLERSTELLER'),
(1, 'REQUIREMENTS_ENGINEER'),
(3, 'TESTER'),
(5, 'TESTFALLERSTELLER'),
(4, 'TESTMANAGER'),
(6, 'REQUIREMENTS_ENGINEER');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_seq`
--

CREATE TABLE `user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `user_seq`
--

INSERT INTO `user_seq` (`next_val`) VALUES
(1);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `requirement`
--
ALTER TABLE `requirement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgw1rwyk7yv1ajvhsr606bbet` (`created_by_user_ref`);

--
-- Indizes für die Tabelle `testcase`
--
ALTER TABLE `testcase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtnv3kltgb469qmcgtvk8c46my` (`created_by_user_ref`),
  ADD KEY `FKdbb81fqsf793qhq6u0ham4mqw` (`requirement_ref`);

--
-- Indizes für die Tabelle `testrun`
--
ALTER TABLE `testrun`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcdeumnhi22onkgs8q66cq5y9f` (`assigned_to_user_ref`),
  ADD KEY `FK756dx8lxpstbd9hc2nesr72j2` (`created_by_user_ref`);

--
-- Indizes für die Tabelle `testrun_testcase`
--
ALTER TABLE `testrun_testcase`
  ADD KEY `FKhar8qb3swryucstiajknpubpu` (`TestCase_ref`),
  ADD KEY `FK8v3kqrar73ps9s6bthb3vo4pk` (`TestRun_ref`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `user_roles`
--
ALTER TABLE `user_roles`
  ADD KEY `FKi81fp6mx433heb7dvbxqaqvpv` (`User_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `requirement`
--
ALTER TABLE `requirement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT für Tabelle `testcase`
--
ALTER TABLE `testcase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `testrun`
--
ALTER TABLE `testrun`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `requirement`
--
ALTER TABLE `requirement`
  ADD CONSTRAINT `FKgw1rwyk7yv1ajvhsr606bbet` FOREIGN KEY (`created_by_user_ref`) REFERENCES `user` (`id`);

--
-- Constraints der Tabelle `testcase`
--
ALTER TABLE `testcase`
  ADD CONSTRAINT `FKdbb81fqsf793qhq6u0ham4mqw` FOREIGN KEY (`requirement_ref`) REFERENCES `requirement` (`id`),
  ADD CONSTRAINT `FKtnv3kltgb469qmcgtvk8c46my` FOREIGN KEY (`created_by_user_ref`) REFERENCES `user` (`id`);

--
-- Constraints der Tabelle `testrun`
--
ALTER TABLE `testrun`
  ADD CONSTRAINT `FK756dx8lxpstbd9hc2nesr72j2` FOREIGN KEY (`created_by_user_ref`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKcdeumnhi22onkgs8q66cq5y9f` FOREIGN KEY (`assigned_to_user_ref`) REFERENCES `user` (`id`);

--
-- Constraints der Tabelle `testrun_testcase`
--
ALTER TABLE `testrun_testcase`
  ADD CONSTRAINT `FK8v3kqrar73ps9s6bthb3vo4pk` FOREIGN KEY (`TestRun_ref`) REFERENCES `testrun` (`id`),
  ADD CONSTRAINT `FKhar8qb3swryucstiajknpubpu` FOREIGN KEY (`TestCase_ref`) REFERENCES `testcase` (`id`);

--
-- Constraints der Tabelle `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKi81fp6mx433heb7dvbxqaqvpv` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
