-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 22 Lis 2021, 13:52
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `clinic`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `doctors`
--

CREATE TABLE `doctors` (
  `DoctorId` smallint(5) UNSIGNED NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `EmployedSinceDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ProfessionalTitle` varchar(20) NOT NULL,
  `SpecialtyId` smallint(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `doctors`
--

INSERT INTO `doctors` (`DoctorId`, `FirstName`, `LastName`, `EmployedSinceDate`, `ProfessionalTitle`, `SpecialtyId`) VALUES
(1, 'Maciej', 'Makowski', '2002-11-30 23:00:00', 'dr hab. n. med', 49),
(2, 'Gustaw', 'Jasinski', '2000-02-29 23:00:00', 'prof. n. med', 38),
(3, 'Borys', 'Malinowski', '2006-10-31 23:00:00', 'lek. med', 46),
(4, 'Adam', 'Mazurek', '2015-04-30 22:00:00', 'dr hab. n. med', 14),
(5, 'Eryk', 'Baran', '2004-08-31 22:00:00', 'prof. n. med', 8),
(6, 'Allan', 'Tomaszewski', '2002-07-31 22:00:00', 'dr n. med.', 45),
(7, 'Kazimierz', 'Zalewski', '2017-10-31 23:00:00', 'dr hab. n. med', 33),
(8, 'Ludwik', 'Lis', '2002-07-31 22:00:00', 'dr n. med.', 5),
(9, 'Alexander', 'Bobrowski', '2021-11-20 09:07:03', 'lek. med', 8),
(10, 'Denis', 'Ziarnowski', '2007-08-31 22:00:00', 'lek. med', 19),
(11, 'Bruno', 'Jankowski', '2008-12-31 23:00:00', 'dr hab. n. med', 38),
(12, 'Pawel', 'Podolski', '2021-11-20 09:07:07', 'dr n. med.', 11),
(13, 'Gabriel', 'Zakrzewski', '2015-02-28 23:00:00', 'dr n. med.', 51),
(14, 'Alfred', 'Laskowski', '2007-11-30 23:00:00', 'dr hab. n. med', 22),
(15, 'Klaudiusz', 'Duda', '2017-08-31 22:00:00', 'lek. med', 42),
(16, 'Franciszek', 'Michalak', '2009-10-31 23:00:00', 'prof. n. med', 41),
(17, 'Dawid', 'Karski', '2013-08-31 22:00:00', 'dr hab. n. med', 53),
(18, 'Anastazy', 'Kazmierczak', '2007-05-31 22:00:00', 'lek. med', 32),
(19, 'Grzegorz', 'Cieplak', '2011-04-30 22:00:00', 'lek. med', 16),
(20, 'Gracjan', 'Szulc', '2012-07-31 22:00:00', 'dr hab. n. med', 46),
(21, 'Balbina', 'Witkowska', '2017-01-31 23:00:00', 'lek. med', 30),
(22, 'Andrea', 'Jakubowska', '2017-07-31 22:00:00', 'prof. n. med', 33),
(23, 'Elwira', 'Sikora', '2013-09-30 22:00:00', 'lek. med', 53),
(24, 'Hortensja', 'Wasilewska', '2003-09-30 22:00:00', 'lek. med', 9),
(25, 'Joanna', 'Kucharska', '2012-04-30 22:00:00', 'lek. med', 17),
(26, 'Ewa', 'Kawecka', '2005-04-30 22:00:00', 'dr hab. n. med', 50),
(27, 'Bogna', 'Malinowska', '2015-11-30 23:00:00', 'lek. med', 31),
(28, 'Bernadetta', 'Wysocka', '2006-08-31 22:00:00', 'dr hab. n. med', 18),
(29, 'Alina', 'Ostrowska', '2006-12-31 23:00:00', 'dr hab. n. med', 1),
(30, 'Agnieszka', 'Rutkowska', '2002-08-31 22:00:00', 'prof. n. med', 54);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `illnesses`
--

CREATE TABLE `illnesses` (
  `IllnessId` int(10) UNSIGNED NOT NULL,
  `IllnessName` varchar(100) NOT NULL,
  `TypeId` smallint(5) UNSIGNED NOT NULL,
  `IsCurable` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `SpecialtyId` smallint(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `illness_types`
--

CREATE TABLE `illness_types` (
  `TypeId` smallint(5) UNSIGNED NOT NULL,
  `Type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `patients`
--

CREATE TABLE `patients` (
  `PatientId` int(10) UNSIGNED NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `RegistrationDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `LastVisitDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `VisitsNumber` smallint(5) UNSIGNED NOT NULL DEFAULT 0,
  `PESEL` char(11) NOT NULL DEFAULT '0',
  `HasInsurance` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `DoctorId` smallint(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `patients`
--

INSERT INTO `patients` (`PatientId`, `FirstName`, `LastName`, `RegistrationDate`, `LastVisitDate`, `VisitsNumber`, `PESEL`, `HasInsurance`, `DoctorId`) VALUES
(1, 'Oksana', 'Adamska', '2000-02-29 23:00:00', '2021-11-30 23:00:00', 10, '92010321121', 1, 13),
(2, 'Beata', 'Jankowska', '2013-05-31 22:00:00', '2018-10-31 23:00:00', 20, '73110537761', 1, 10),
(3, 'Daniela', 'Kucharska', '2013-10-31 23:00:00', '2018-10-31 23:00:00', 19, '64111257621', 1, 20),
(4, 'Beata', 'Lisowska', '2013-09-30 22:00:00', '2018-04-30 22:00:00', 19, '89080680161', 1, 18),
(5, 'Nikola', 'Bolkowska', '2006-05-31 22:00:00', '2021-09-30 22:00:00', 12, '92090222021', 1, 18),
(6, 'Krystyna', 'Czerwiecka', '2014-08-31 22:00:00', '2019-12-31 23:00:00', 3, '59032598921', 1, 13),
(7, 'Franciszka', 'Stopecka', '2015-10-31 23:00:00', '2018-06-30 22:00:00', 13, '65012020241', 1, 17),
(8, 'Ewa', 'Malinowska', '2012-07-31 22:00:00', '2020-03-31 22:00:00', 3, '74031994601', 1, 22),
(9, 'Maria', 'Janowska', '2003-11-30 23:00:00', '2018-10-31 23:00:00', 3, '69111673301', 1, 1),
(10, 'Berenika', 'Mrozowska', '2006-09-30 22:00:00', '2020-10-31 23:00:00', 3, '60041083001', 1, 7),
(11, 'Angelika', 'Zieleniecka', '2001-06-30 22:00:00', '2019-07-31 22:00:00', 4, '90021741701', 1, 7),
(12, 'Otylia', 'Horecka', '2004-04-30 22:00:00', '2020-01-31 23:00:00', 15, '71091973681', 1, 24),
(13, 'Anatolia', 'Kucharska', '2009-05-31 22:00:00', '2020-05-31 22:00:00', 12, '78060575621', 1, 19),
(14, 'Alana', 'Stopiecka', '2014-09-30 22:00:00', '2018-08-31 22:00:00', 22, '71041691441', 1, 17),
(15, 'Bogna', 'Kuchcicka', '2015-07-31 22:00:00', '2019-09-30 22:00:00', 19, '84041255441', 1, 12),
(16, 'Emilia', 'Wysocka', '2016-07-31 22:00:00', '2019-06-30 22:00:00', 17, '65061456021', 1, 6),
(17, 'Katarzyna', 'Kowalska', '2009-10-31 23:00:00', '2019-06-30 22:00:00', 9, '64112573621', 1, 3),
(18, 'Florencja', 'Witkowska', '2018-11-30 23:00:00', '2021-09-30 22:00:00', 6, '94022458601', 1, 20),
(19, 'Roksana', 'Laskowska', '2001-08-31 22:00:00', '2017-12-31 23:00:00', 18, '65020247781', 1, 1),
(20, 'Aniela', 'Gorycka', '2015-04-30 22:00:00', '2018-03-31 22:00:00', 14, '63071291841', 1, 15),
(21, 'Zofia', 'Chmielewska', '2011-09-30 22:00:00', '2018-08-31 22:00:00', 14, '92031532761', 1, 14),
(22, 'Stefania', 'Lubieniecka', '2013-03-31 22:00:00', '2021-04-30 22:00:00', 9, '79122488961', 1, 28),
(23, 'Ewelina', 'Wiszniewska', '2006-07-31 22:00:00', '2019-04-30 22:00:00', 25, '71111867381', 1, 10),
(24, 'Pamela', 'Zalewska', '2017-04-30 22:00:00', '2017-12-31 23:00:00', 19, '98011750021', 1, 24),
(25, 'Franciszka', 'Jupiterska', '2017-10-31 23:00:00', '2021-06-30 22:00:00', 10, '97122021661', 1, 25),
(26, 'Anastazja', 'Galijska', '2000-08-31 22:00:00', '2020-10-31 23:00:00', 23, '85080184201', 1, 3),
(27, 'Florentyna', 'Kowalska', '2000-02-29 23:00:00', '2021-01-31 23:00:00', 12, '62022248121', 1, 26),
(28, 'Andrea', 'Maciejewska', '2010-04-30 22:00:00', '2019-11-30 23:00:00', 2, '55021236981', 1, 30),
(29, 'Rozalia', 'Sadowska', '2017-04-30 22:00:00', '2020-09-30 22:00:00', 16, '60072541381', 1, 24),
(30, 'Edyta', 'Przybylska', '2012-12-31 23:00:00', '2021-07-31 22:00:00', 4, '57081891881', 1, 15);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `specialties`
--

CREATE TABLE `specialties` (
  `SpecialtyId` smallint(5) UNSIGNED NOT NULL,
  `SpecialtyName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `specialties`
--

INSERT INTO `specialties` (`SpecialtyId`, `SpecialtyName`) VALUES
(1, 'Alergologia'),
(2, 'Anestezjologia'),
(3, 'Angiochirurgia'),
(4, 'Balneologia'),
(5, 'Balneologia i medycyna fizykalna'),
(6, 'Bariatria'),
(7, 'Chirurgia'),
(8, 'Dermatologia'),
(9, 'Endokrynologia ginekologiczna i rozrodczosc'),
(10, 'Epidemiologia'),
(11, 'Medycyna estetyczna'),
(12, 'Farmakologia kliniczna'),
(13, 'Gastroenterologia'),
(14, 'Genetyka kliniczna'),
(15, 'Geriatria'),
(16, 'Ginekologia'),
(17, 'Ginekologia onkologiczna'),
(18, 'Immunologia'),
(19, 'Immunologia kliniczna'),
(20, 'Kardiologia'),
(21, 'Medycyna paliatywna'),
(22, 'Medycyna pracy'),
(23, 'Medycyna ratunkowa'),
(24, 'Medycyna rodzinna'),
(25, 'Medycyna sadowa'),
(26, 'Medycyna sportowa'),
(27, 'Medycyna transportu'),
(28, 'Medycyna tropikalna'),
(29, 'Medycyna wojskowa'),
(30, 'Mikrobiologia lekarska'),
(31, 'Nefrologia'),
(32, 'Neonatologia'),
(33, 'Neurologia'),
(34, 'Neuropatologia'),
(35, 'Okulistyka'),
(36, 'Onkologia'),
(37, 'Onkologia ginekologiczna'),
(38, 'Onkologia i hematologia dziecieca'),
(39, 'Onkologia kliniczna'),
(40, 'Ortopedia'),
(41, 'Ortopedia i traumatologia narzadu ruchu'),
(42, 'Otorynolaryngologia'),
(43, 'Patologia'),
(44, 'Pediatria'),
(45, 'Pediatria metaboliczna'),
(46, 'Poloznictwo'),
(47, 'Psychiatria'),
(48, 'Pulmonologia'),
(49, 'Radiologia'),
(50, 'Rehabilitacja medyczna'),
(51, 'Reumatologia'),
(52, 'Seksuologia'),
(53, 'Telerehabilitacja'),
(54, 'Toksykologia'),
(55, 'Toksykologia kliniczna'),
(56, 'Transfuzjologia'),
(57, 'Urologia'),
(58, 'Wenerologia');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `treatments`
--

CREATE TABLE `treatments` (
  `TreatmentId` int(10) UNSIGNED NOT NULL,
  `PatientId` int(10) UNSIGNED NOT NULL,
  `IllnessId` int(10) UNSIGNED NOT NULL,
  `DoctorId` smallint(5) UNSIGNED DEFAULT NULL,
  `DiagnosisDate` date NOT NULL,
  `TreatmentFinishDate` date NOT NULL,
  `IsIllnessCured` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `VisitsNumber` smallint(5) UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`DoctorId`),
  ADD KEY `DOC_SPEC` (`SpecialtyId`);

--
-- Indeksy dla tabeli `illnesses`
--
ALTER TABLE `illnesses`
  ADD PRIMARY KEY (`IllnessId`),
  ADD KEY `ILL_TYPE` (`TypeId`),
  ADD KEY `ILL_SPEC` (`SpecialtyId`);

--
-- Indeksy dla tabeli `illness_types`
--
ALTER TABLE `illness_types`
  ADD PRIMARY KEY (`TypeId`);

--
-- Indeksy dla tabeli `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`PatientId`),
  ADD KEY `PatDocFK` (`DoctorId`);

--
-- Indeksy dla tabeli `specialties`
--
ALTER TABLE `specialties`
  ADD PRIMARY KEY (`SpecialtyId`);

--
-- Indeksy dla tabeli `treatments`
--
ALTER TABLE `treatments`
  ADD PRIMARY KEY (`TreatmentId`),
  ADD KEY `TREATDOC` (`DoctorId`),
  ADD KEY `TREATPATS` (`PatientId`),
  ADD KEY `TREATILL` (`IllnessId`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `doctors`
--
ALTER TABLE `doctors`
  MODIFY `DoctorId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT dla tabeli `illnesses`
--
ALTER TABLE `illnesses`
  MODIFY `IllnessId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `illness_types`
--
ALTER TABLE `illness_types`
  MODIFY `TypeId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `patients`
--
ALTER TABLE `patients`
  MODIFY `PatientId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT dla tabeli `specialties`
--
ALTER TABLE `specialties`
  MODIFY `SpecialtyId` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT dla tabeli `treatments`
--
ALTER TABLE `treatments`
  MODIFY `TreatmentId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `DOC_SPEC` FOREIGN KEY (`SpecialtyId`) REFERENCES `specialties` (`SpecialtyId`);

--
-- Ograniczenia dla tabeli `illnesses`
--
ALTER TABLE `illnesses`
  ADD CONSTRAINT `ILL_SPEC` FOREIGN KEY (`SpecialtyId`) REFERENCES `specialties` (`SpecialtyId`),
  ADD CONSTRAINT `ILL_TYPE` FOREIGN KEY (`TypeId`) REFERENCES `illness_types` (`TypeId`);

--
-- Ograniczenia dla tabeli `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `PatDocFK` FOREIGN KEY (`DoctorId`) REFERENCES `doctors` (`DoctorId`);

--
-- Ograniczenia dla tabeli `treatments`
--
ALTER TABLE `treatments`
  ADD CONSTRAINT `TREATDOC` FOREIGN KEY (`DoctorId`) REFERENCES `doctors` (`DoctorId`) ON DELETE SET NULL,
  ADD CONSTRAINT `TREATILL` FOREIGN KEY (`IllnessId`) REFERENCES `illnesses` (`IllnessId`),
  ADD CONSTRAINT `TREATPATS` FOREIGN KEY (`PatientId`) REFERENCES `patients` (`PatientId`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
