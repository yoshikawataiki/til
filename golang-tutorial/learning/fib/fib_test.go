package main

import "testing"

func TestFib(t *testing.T) {
	if fib(0) != 0 {
		t.Fatalf("want 0, got %d", fib(0))
	}
	if fib(1) != 1 {
		t.Fatalf("want 1, got %d", fib(1))
	}
}
