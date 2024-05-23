-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: sql708.your-server.de
-- Erstellungszeit: 23. Mai 2024 um 18:49
-- Server-Version: 10.11.6-MariaDB-0+deb12u1
-- PHP-Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ss24_bam1_busint`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `BankChannel`
--

CREATE TABLE `BankChannel` (
  `bankName` text NOT NULL,
  `bankChannel` text NOT NULL,
  `minCreditScore` float NOT NULL,
  `minLoanAmount` float NOT NULL,
  `maxLoanAmount` float NOT NULL,
  `minTerm` int(11) NOT NULL,
  `maxTerm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Daten für Tabelle `BankChannel`
--

INSERT INTO `BankChannel` (`bankName`, `bankChannel`, `minCreditScore`, `minLoanAmount`, `maxLoanAmount`, `minTerm`, `maxTerm`) VALUES
('Commerzbank', 'commerzbankChannel', 750, 15000, 35000, 12, 48),
('Deutsche Bank', 'deutscheBankChannel', 450, 5000, 20000, 12, 60),
('Barclays Bank', 'barclaysBankChannel', 300, 10000, 30000, 6, 60),
('HSBC Bank', 'hsbcBankChannel', 150, 5000, 25000, 3, 72);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CreditOffers`
--

CREATE TABLE `CreditOffers` (
  `timestamp` timestamp NOT NULL,
  `UUID` uuid NOT NULL,
  `ssn` varchar(255) NOT NULL,
  `amount` float NOT NULL,
  `term` int(11) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Daten für Tabelle `CreditOffers`
--

INSERT INTO `CreditOffers` (`timestamp`, `UUID`, `ssn`, `amount`, `term`, `score`) VALUES
('2024-05-07 22:00:00', 'f408b215-003d-44af-b6c4-01d7bf5529a6', '123-112-7891', 500110, 120, 508),
('2024-05-08 20:44:00', '3fd2ed85-0c8b-43bb-9006-64b90ed991c6', '123-112-7891', 50010, 120, 504),
('2024-05-08 20:44:28', 'a9016bf9-a6d3-4697-be50-af35b4bf0fcd', '123-112-7891', 500160, 120, 453),
('2024-05-08 20:45:37', '4f6ab27d-6fc8-4c19-9b81-66a2c2e0f991', '123-112-7891', 500160, 120, 664),
('2024-05-08 20:51:27', '32bc305e-f613-462a-9038-c9f23cb2f73b', '123-112-7891', 500160, 120, 653),
('2024-05-08 21:04:33', '94a8c30a-9e6e-4b43-816a-4ba88f6b988a', '123-112-7891', 500160, 120, 402),
('2024-05-08 21:04:42', '2c6a3bdc-4604-4cce-bd52-3602a5ab770d', '123-112-7891', 500160, 120, 804),
('2024-05-09 12:51:39', '64df5977-9b08-4375-ba3c-777b0dbdf0c0', '123-112-7891', 500160, 120, 434),
('2024-05-09 13:03:06', 'b56c9759-858a-411a-8ab1-b0a448163025', '123-112-7891', 500160, 120, 332),
('2024-05-09 14:10:57', '166e0201-1cf7-48da-894a-0ab057f9df5e', '123-112-7891', 500160, 120, 381),
('2024-05-09 17:35:30', '235e05ce-6f39-4483-9c9e-d06e3a78a81a', '123-112-7891', 500160, 120, 761),
('2024-05-09 17:46:26', '8ef3e5d5-c493-41ff-ac10-a6ababced294', '123-112-7891', 500160, 120, 751),
('2024-05-09 20:45:30', '86ea9ccb-b439-4b0f-8098-f89554046c2f', '123-112-7891', 500160, 120, 802),
('2024-05-09 20:48:36', '378e15d3-45cf-492c-8e0e-9c0ff034032d', '123-112-712891', 500160, 120, 737),
('2024-05-11 22:56:44', '91c64592-e90c-4a56-924b-880517a89280', '123-112-712891', 500160, 120, 748),
('2024-05-11 23:06:38', '10654675-420b-4759-8e3c-8af20e8f61a4', '123-112-712891', 500160, 120, 358),
('2024-05-11 23:13:24', '0d82d817-fb4c-4326-8034-c9576072dd4c', '123-112-712891', 500160, 120, 737),
('2024-05-11 23:18:44', '29e082ff-2b8a-43a3-a5ff-30ac499f4af7', '123-112-712891', 500160, 120, 673),
('2024-05-11 23:25:00', '20771841-0651-41cb-affc-54536e0db986', '123-112-712891', 500160, 120, 557),
('2024-05-11 23:30:30', 'edac4c45-3eed-4e9a-8ae2-57709115ea9d', '123-112-712891', 500160, 120, 772),
('2024-05-11 23:38:14', 'a3e75480-aa2a-4a85-be52-7901c27e7a8c', '123-112-712891', 500160, 120, 687),
('2024-05-11 23:56:54', 'a1cc512a-f840-4277-9318-cf67e2e51b49', '123-112-712891', 500160, 120, 813),
('2024-05-12 00:02:29', '6aa85e5e-ecbf-4d4d-9f1c-bd0f95ca2765', '123-112-712891', 500160, 120, 654),
('2024-05-12 14:56:09', 'd38a12ce-35f7-4ca3-87a2-9d3168626d08', '123-112-712891', 500160, 120, 483),
('2024-05-12 15:03:44', '2e69dae7-cc86-4c10-8691-55f755066df2', '123-112-712891', 500160, 120, 367),
('2024-05-14 17:04:56', '218cbd26-c8a0-48ba-ba8c-2834419666ab', '123-112-712891', 6000, 13, 510),
('2024-05-14 20:21:24', '3424349e-0de3-48d9-a3ad-ce7e1e902b56', '123-112-712891', 16000, 16, 829),
('2024-05-14 20:21:32', '96a2f4b0-de3b-46ea-b21a-51278c0a0f30', '123-112-712891', 16100, 16, 334),
('2024-05-22 19:29:59', '6d31b3a5-39ca-4dba-83de-af4188531a07', '123-112-712891', 16100, 16, 783),
('2024-05-22 21:28:46', 'a3f87810-ca66-41dc-a920-8d392a34ec47', '123-112-712891', 16000, 16, 738),
('2024-05-23 16:12:13', 'f4950214-2971-4cee-b204-772858b58bcf', '123-112-712891', 16000, 16, 966),
('2024-05-23 16:12:41', 'e5211b0f-c8ef-4be7-8eff-4d5841d927c0', '123-112-712891', 6000, 16, 195),
('2024-05-23 16:13:56', 'd83881e2-f7b7-4734-a1a2-0e85898f98e7', '123-112-712891', 67000, 16, 89),
('2024-05-23 16:14:09', '8b5b726a-c4e0-4e67-a213-5ab3d47d34f6', '123-112-712891', 10000, 16, 616);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
