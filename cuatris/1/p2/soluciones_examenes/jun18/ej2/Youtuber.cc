#include "Youtuber.h"

ostream& operator<<(ostream& os, const Youtuber& y) {
	os << y.nick << ", url=" << y.url << ", profits=" << y.profits << endl;
	return os;
}

Youtuber::Youtuber(string nick, string url){
	if(nick == "" || url == "")
		throw 0;
	else if(url.find("http://www.youtube.com/channel/") != 0)
		throw 1;

	this->nick = nick;
	this->url = url;
	penalized = false;
	profits = 0;
}

void Youtuber::addProfits(float profits){
	this->profits += profits;
}
