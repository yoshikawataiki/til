package main

import (
	"flag"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"net/http/httputil"
	"os"
	"strings"
)

type params struct {
	rawurl, method string
	headers        http.Header
	dump           bool
}

func curl(p params) (*http.Response, error) {
	req, err := http.NewRequest(p.method, p.rawurl, nil)
	if err != nil {
		return nil, err
	}
	req.Header = p.headers
	if p.dump {
		b, err := httputil.DumpRequestOut(req, false)
		if err != nil {
			return nil, err
		}
		fmt.Printf("dump = %+v\n", string(b))
	}
	return http.DefaultClient.Do(req)
}

type headers []string

func (h *headers) String() string {
	return strings.Join(*h, ",")
}

func (h *headers) Set(value string) error {
	*h = append(*h, value)
	return nil
}

var h headers

func main() {
	flag.Var(&h, "H", "My HTTP Request Header!")
	var (
		method = flag.String("X", "GET", "method")
		dump   = flag.Bool("dump", false, "dump or not")
	)
	flag.Parse()
	rawurl := flag.Arg(0)
	p := params{
		rawurl: rawurl,
		method: *method,
		dump:   *dump,
	}

	header := make(http.Header, 0)

	for _, hh := range h {
		hb := strings.Split(hh, ":")
		if len(hb) != 2 {
			log.Fatal("want valid header")
		}
		header.Set(hb[0], hb[1])
	}

	p.headers = header
	resp, err := curl(p)
	if err != nil {
		panic(err)
	}
	b, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err)
	}
	fmt.Fprintf(os.Stdout, "%s", string(b))
}
