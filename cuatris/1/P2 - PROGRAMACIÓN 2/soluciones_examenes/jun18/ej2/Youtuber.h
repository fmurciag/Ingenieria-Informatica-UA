#ifndef _YOUTUBER_
#define _YOUTUBER_

#include <iostream>
using namespace std;

class Youtuber{
	friend ostream& operator<<(ostream& os, const Youtuber& y);
private:
	string nick, url;
	bool penalized;
	float profits;
public:
	Youtuber(string nick, string url);
	string getNick() const { return nick; }
	string getUrl() const { return url; }
	bool isPenalized() const { return penalized; }
	void setPenalized(bool penalized) { this->penalized = penalized; }
	void addProfits(float profits);
};

#endif
