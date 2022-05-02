#ifndef _CATEGORY_
#define _CATEGORY_

#include "Youtuber.h"
#include <vector>

class Category{
	friend ostream& operator<<(ostream& os, const Category& c);
private:
	string description;
	int percentage;
	vector<string> forbiddenNicks;
	vector<Youtuber> youtubers;
	bool isForbidden(string nick) const;
	int findYoutuber(string nick) const;
public:
	Category(string description, int percentage);
	void addForbiddenNick(string nick);
	void addYoutuber(string nick, string url);
	void penalize(string nick);
	void shareProfits(float profits);
};

#endif
