package main

import (
	"database/sql"
	"log"
	"os"

	_ "github.com/go-sql-driver/mysql"
)

type Post struct {
	postID int
	body   string
}

func Create(db *sql.DB, body string) {
	_, err := db.Exec(
		`INSERT INTO reports(body) VALUES (?)`, body,
	)
	if err != nil {
		log.Fatal(err)
	}
}

func Update(db *sql.DB, body string) {
	_, err := db.Exec(
		`UPDATE TO reports(body) VALUES (?)`, body,
	)
	if err != nil {
		log.Fatal(err)
	}
}

func main() {
	body := os.Args[1]
	// 日報用のテーブル reports を用意し、保存
	db, err := sql.Open("mysql",
		"root:password@tcp(localhost:3306)/reports")
	if err != nil {
		log.Fatal(err)
	}
	Create(db, body)
	defer db.Close()
}
