package main

import (
	"encoding/json"
	"log"
	"net/http"
	"strings"

	"golang.org/x/net/html"
)

type Page struct {
	Url         string `json:"url"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Ogtitle     string `json:ogtitle`
	Ogimage     string `json:ogimage`
}

type Pages []*Page

func Get(url string) (*Page, error) {
	resp, err := http.Get(url)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	doc, err := html.Parse(resp.Body)
	if err != nil {
		return nil, err
	}

	// START OMIT
	page := &Page{}
	page.Url = url
	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.ElementNode && n.Data == "title" {
			page.Title = n.FirstChild.Data
		}
		if n.Type == html.ElementNode && n.Data == "meta" {
			if isDescription(n.Attr) {
				for _, attr := range n.Attr {
					if attr.Key == "content" {
						page.Description = attr.Val
					}
				}
			} else if isOgTitle(n.Attr) {
				for _, attr := range n.Attr {
					if attr.Key == "content" {
						page.Ogtitle = attr.Val
					}
				}
			} else if isImage(n.Attr) {
				for _, attr := range n.Attr {
					if attr.Key == "content" {
						page.Ogimage = attr.Val
					}
				}
			}
		}
		for c := n.FirstChild; c != nil; c = c.NextSibling {
			f(c)
		}
	}
	f(doc)
	// END OMIT
	return page, nil
}

func isDescription(attrs []html.Attribute) bool {
	for _, attr := range attrs {
		if attr.Key == "name" && attr.Val == "description" {
			return true
		}
	}
	return false
}

func isImage(attrs []html.Attribute) bool {
	for _, attr := range attrs {
		if attr.Key == "property" && attr.Val == "og:image" {
			return true
		}
	}
	return false
}

func isOgTitle(attrs []html.Attribute) bool {
	for _, attr := range attrs {
		if attr.Key == "property" && attr.Val == "og:title" {
			return true
		}
	}
	return false
}

func handler() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		rawurl := r.FormValue("url")
		urls := strings.Split(rawurl, ",")
		for i := 0; i < len(urls); i++ {
			if urls[i] == "" {
				http.Error(w, "url not specified", http.StatusBadRequest)
				return
			}
			p, err := Get(urls[i])
			if err != nil {
				http.Error(w, "request failed", http.StatusInternalServerError)
				return
			}
			w.Header().Set("Content-Type", "application/json")
			enc := json.NewEncoder(w)
			if err := enc.Encode(p); err != nil {
				http.Error(w, "encoding failed", http.StatusInternalServerError)
				return
			}
		}
	})
}

func main() {
	handler()
	log.Fatal(http.ListenAndServe(":8080", nil))
}
