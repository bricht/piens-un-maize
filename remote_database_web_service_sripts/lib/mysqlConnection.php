<?php

	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Update product identity aissigned to particualr barcode
	**/
	
	function getMysqlConnection() {
		$root = $_SERVER['DOCUMENT_ROOT'];
		$loginurl = parse_ini_file($root . 'piens_un_maize_db/init/login_url.ini');
		$login = parse_ini_file($loginurl['url']);
		
		$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
			return false;
		}
		return $conn;
	}
	
	function parseToJSON($stmt) {
		$result = $stmt->get_result();
		$jsonData = array();
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$jsonData[] = $row;
			}
			return json_encode($jsonData);
		} else {
			return "[]";
		}
	}
	
?>