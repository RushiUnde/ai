class Node:
    def _init_(self):
        self.cost = 0
        self.hval = 0
        self.board = []

    def Hfun(self, Goal):
        count = 0
        for i in range(3):
            for j in range(3):
                if self.board[i][j] != Goal.board[i][j]:
                    count += 1
        self.hval = count

    def PrintBoard(self):
        for i in range(3):
            for j in range(3):
                print(self.board[i][j], end=" ")
            print()
        print()


class Position:
    def _init_(self):
        self.x = 0
        self.y = 0

    def zeroposition(self, temp):
        for i in range(3):
            for j in range(3):
                if temp.board[i][j] == 0:
                    self.x = i
                    self.y = j
                    return


# Goal State
Goal = Node()
Goal.board = [[1, 2, 3], [4, 5, 6], [7, 8, 0]]
Goal.hval = 0

# Initial State
Initial = Node()
Initial.board = [[4, 0, 1], [5, 2, 3], [7, 6, 8]]
Initial.Hfun(Goal)

minimum = 10
curr = Position()
TempBoard = Node()

while Initial.hval < minimum and Initial.hval != 0:
    Initial.PrintBoard()
    print("Heuristic value =", Initial.hval, "\n")
    print("*****************\n")
    minimum = Initial.hval
    TempBoard = Initial
    curr.zeroposition(TempBoard)

    if curr.x + 1 < 3:
        print("x + 1\n")
        TempBoard.board[curr.x + 1][curr.y], TempBoard.board[curr.x][curr.y] = (
            TempBoard.board[curr.x][curr.y],
            TempBoard.board[curr.x + 1][curr.y],
        )
        TempBoard.Hfun(Goal)
        if TempBoard.hval < Initial.hval:
            Initial = TempBoard
            continue
    if curr.y + 1 < 3:
        print("y + 1\n")
        TempBoard.board[curr.x][curr.y + 1], TempBoard.board[curr.x][curr.y] = (
            TempBoard.board[curr.x][curr.y],
            TempBoard.board[curr.x][curr.y + 1],
        )
        TempBoard.Hfun(Goal)
        if TempBoard.hval < Initial.hval:
            Initial = TempBoard
            continue
    if curr.x - 1 >= 0:
        print("x - 1\n")
        TempBoard.board[curr.x - 1][curr.y], TempBoard.board[curr.x][curr.y] = (
            TempBoard.board[curr.x][curr.y],
            TempBoard.board[curr.x - 1][curr.y],
        )
        TempBoard.Hfun(Goal)
        if TempBoard.hval < Initial.hval:
            Initial = TempBoard
            continue
    if curr.y - 1 >= 0:
        print("y - 1\n")
        TempBoard.board[curr.x][curr.y - 1], TempBoard.board[curr.x][curr.y] = (
            TempBoard.board[curr.x][curr.y],
            TempBoard.board[curr.x][curr.y - 1],
        )
        TempBoard.Hfun(Goal)
        if TempBoard.hval < Initial.hval:
            Initial = TempBoard
            continue

Initial.PrintBoard()
print("Heuristic value =", Initial.hval, "\n")