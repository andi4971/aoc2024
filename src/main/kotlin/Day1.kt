import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {

    val sb = StringBuilder(mainStr)
    sb.appendLine()
    val count = 70000
    for (i in 0 until count) {
        sb.appendLine(getFuncString(i))
    }
    Files.writeString(Path.of("superlong.mcpp"), sb.toString())
}

fun getFuncString(i: Int): String {
    return """
      void printWorld$i(int gen) {
      int alive, i, j, k;

      cout << endl << endl << endl << endl << endl;
      cout << "generation " << gen << ":" << endl << endl;
      
      i = 0;
      cout << "+";
      while (i < size) {
        cout << "--";
        i++;
      } // while
      cout << "-+" << endl;
      
      alive = 0;
      i = 0;
      while (i < size) {
        cout << "| ";
        j = 0;
        while (j < size) {
          if (world[map(i, j)]) {  // cell (i,j) is alive
            alive++;
            cout << "@ ";
          } else                    // cell (i,j) is dead
            cout << "  ";
          j++;
        } // while
        cout << "|" << endl;
        i++;
      } // while

      i = 0;
      cout << "+";
      while (i < size) {
        cout << "--";
        i++;
      } // while
      cout << "-+" << endl;

      cout << endl << "=> " << alive << " cells alive" << endl;

      // take some time
      k = 13;
      i = 1;
      while (i < 10000) {
        j = 1;
        while (j < 5000) {
          k = (i * j / j) % 13;
          j++;
        } // while
        i++;
      } // while
      
    } // printWorld
    """.trimIndent()
}

val mainStr = """
const int maxSize       = 100;
const int maxIterations = 10000;

int   size;        // dimension of 2D world
bool* world;       // 1D array of all cells (true = alive, false = dead)
int   iterations;  // number of iterations


// --- random number generator ---

const int a =  3421;  // 2 <= a < m
const int k =     1;  // 0 <= k < m
const int m = 32768;  // = 2^15 

int   x;           // current random number

int random() {
  x = (a * x + k) % m;
  cout <<"random number: "<< x << endl;
  return x;
} // random


// --- function declarations (prototypes)  ---

int  map(int x, int y);             // map 2D index to 1D index
int  countNeighbors(int x, int y);  // count alive neighbors of cell (x,y)
void initialize(void);              // randomly populate world
void step(void);                    // calculate next interation
void printWorld(int gen);           // display all cells


// --- function definitions (implementations) ---

int map(int x, int y) {
  return y * size + x;
} // map

int countNeighbors(int x, int y) {
  int neighbors = 0;

  if (world[map((x - 1 + size) % size, (y - 1 + size) % size)])
    neighbors++;
  if (world[map( x                   , (y - 1 + size) % size)]) 
    neighbors++;
  if (world[map((x + 1 + size) % size, (y - 1 + size) % size)]) 
    neighbors++;
  if (world[map((x - 1 + size) % size,  y)]) 
    neighbors++;
  if (world[map((x + 1 + size) % size,  y)]) 
    neighbors++;
  if (world[map((x - 1 + size) % size, (y + 1 + size) % size)]) 
    neighbors++;
  if (world[map( x                   , (y + 1 + size) % size)]) 
    neighbors++;
  if (world[map((x + 1 + size) % size, (y + 1 + size) % size)]) 
    neighbors++;

  return neighbors;
} // countNeighbors


void initialize(void) { // create rand. and asymmetrically populated world
  int i;

  i = 0;
  while (i < (size * size)) {
    world[i] = (random() % 5) < 2;  
    i++;
  } // while
} // initialize


void step(void) {
  int i, j;
  int neighbors;
  bool* newWorld;

  newWorld = new bool[size * size];
  i = 0;
  while (i < size) {
    j = 0;
    while (j < size) {
      neighbors = countNeighbors(i, j);
      if (world[map(i, j)]) {  // living cells die if they have 
                               //   less than 2 or more than 3 living neighbors
        newWorld[map(i, j)] = (neighbors == 2) || (neighbors == 3);
      } else {  // dead cells are born 
                //   if they have 3 living neighbors
        newWorld[map(i, j)] = neighbors == 3;
      } // else
      j++;
    } // while
    i++;
  } // while
  world = newWorld;
} // step
    
    void main() {
    
    } // main
""".trimIndent()
