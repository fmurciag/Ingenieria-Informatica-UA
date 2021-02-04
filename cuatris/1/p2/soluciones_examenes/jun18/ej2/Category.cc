#include "Category.h"

ostream& operator<<(ostream& os, const Category& c) {
	os << "---- Category: " << c.description << " ----" << endl;
	for(unsigned i = 0; i < c.youtubers.size(); i++){
		os << c.youtubers[i];
	}
	return os;
}

Category::Category(string description, int percentage) {
	if(description == "" || percentage < 0 || percentage > 100){
		throw exception();
	}
	else{
		this->description = description;
		this->percentage = percentage;
	}
}

void Category::addForbiddenNick(string nick)  {
	if(nick != "")
		forbiddenNicks.push_back(nick);
}

bool Category::isForbidden(string nick) const {
	for(unsigned i = 0; i < forbiddenNicks.size(); i++){
		if(nick.find(forbiddenNicks[i]) != string::npos)
			return true;
	}
	return false;
}

void Category::addYoutuber(string nick, string url) {
	try {
		Youtuber y(nick, url);
		if (isForbidden(nick))
			cout << "ERROR: FORBIDDEN NICK: " << nick << endl;
		else
			youtubers.push_back(y);
	}
	catch(int n) {
		if(n == 0) {
			cout << "ERROR: DATA MISSING" << endl;
		}
		else {
			cout << "ERROR: WRONG URL " << url << endl;
		}
	}
}

int Category::findYoutuber(string nick) const {
	for (unsigned i = 0; i < youtubers.size(); i++){
		if (youtubers[i].getNick() == nick){
			return i;
		}
	}
	return -1;
}

void Category::penalize(string nick) {
	int youtuber = findYoutuber(nick);
	
	if(youtuber != -1){
		if(youtubers[youtuber].isPenalized()){
			addForbiddenNick(nick);
			youtubers.erase(youtubers.begin() + youtuber);
			cout << nick << " REMOVED" << endl;
		}
		else
			youtubers[youtuber].setPenalized(true);		
	}
	else
		cout << "YOUTUBER NOT FOUND" << endl;
}

void Category::shareProfits(float profits) {
	for(unsigned i = 0; i < youtubers.size(); i++) {
		youtubers[i].addProfits((percentage/100.0) * profits/youtubers.size());
	}
}
