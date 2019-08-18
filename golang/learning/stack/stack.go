package main

type Stack struct {
	element []string
}

// Pop is element pop
func (s *Stack) Pop() string {
	// スライスの末尾を取り出す
	if len(s.element) == 0 {
		return ""
	}
	result := s.element[len(s.element)-1]
	s.element = s.element[:len(s.element)-1]
	return result
}

func (s *Stack) Push(ss string) {
	// スライスの最後に新しい要素の追加
	s.element = append(s.element, ss)
}

func main() {

}
