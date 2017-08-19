 <?php
 
	$str_key = str_replace("%20", " ", $_GET['str_key']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "select * from product 
			where name like '%$str_key%'
			or category like '%$str_key%'";
	if($result) {
		$jsonData = array();
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$jsonData[] = $row;
			}
			echo json_encode($jsonData);
		} else {
			echo "0 results";
		}
	} else {
		echo "-Error: sql query failed!";
	}

	$conn->close();
?> 