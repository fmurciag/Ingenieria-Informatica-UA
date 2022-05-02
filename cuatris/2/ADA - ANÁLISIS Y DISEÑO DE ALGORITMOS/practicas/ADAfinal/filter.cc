#include <iostream>
#include <string>

using namespace std;

template<typename T>
void process( const string &message) {
    T value;
    if( ! (cin >> value) ) {
        cerr << "ERROR: can't read '" << message << "'" << endl;
        exit(EXIT_FAILURE);
    }
    cout << message << ": " << value << endl;
}


int main() {

    process<int>("sum of selected values");
    process<int>("expanded nodes");
    process<int>("added nodes");
    process<int>("discarded not factible nodes");
    process<int>("discarded not pomising nodes");
    process<int>("pomising nodes discarded");
    process<int>("completed nodes");
    process<int>("best solution updates");
    process<int>("best solution updates from pessimistic bound");
    process<double>("CPU time");

    int count = 0;
    char c;
    cin >> noskipws;
    while( cin >> c ) {
        if( c == '\n' )
            count++;

        if( count > 1 ) {
            cerr << "ERROR: more than one newline at the end" << endl;
            exit(EXIT_FAILURE);
        }

        if( !isspace(c) ) {
            cerr << "ERROR: some text at the end" << endl;
            exit(EXIT_FAILURE);
        }
    }

    if( count == 0 ) {
        cerr << "ERROR: no newline at the end" << endl;
        exit(EXIT_FAILURE);
    }

    cout << "Output syntax is OK" << endl;

    return 0;
}
