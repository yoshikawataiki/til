package main

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

func main() {
	db, err := sql.Open("mysql",
		"root:password@tcp(localhost:3306)/treasure")
	if err != nil {
		log.Fatal(err)
	}
	// titleを*にすると5カラム分Scanしなきゃいけない
	rows, err := db.Query("select todo_id, title from todos")
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var todoID int
		var title string
		if err := rows.Scan(&todoID, &title); err != nil {
			log.Fatal(err)
		}
		fmt.Printf("ID:%d | title:%s\n", todoID, title)
	}
	defer rows.Close()
	defer db.Close()
}
