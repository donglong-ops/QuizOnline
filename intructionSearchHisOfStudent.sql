-- seach history by status 'passed'
Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = 8 and q.correctAnswer >= (q.numOfQuiz/2)

-- seach history by status 'failded'
Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = 8 and q.correctAnswer < (q.numOfQuiz/2)


---search history by subject and status 'passed'
Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = 8 and q.subjectid = 1 and q.correctAnswer >= (q.numOfQuiz/2)

---search history by subject and status 'faild'
Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = 8 and q.subjectid = 1 and q.correctAnswer < (q.numOfQuiz/2)

-- dạ của chị hết 500k ạ