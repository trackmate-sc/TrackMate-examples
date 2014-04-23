id = getImageID();
newImage("Untitled", "RGB black", 600, 60, 1);
newId = getImageID();

trackId = 0;
w = 36;
h = 36;
start = 0;
stop = 13
for (i = start; i < stop; i++) {
	selectImage(id);
	makeRectangle(9 + trackId * 320, 2 + i * 96, w, h);
	run("Copy");

	selectImage(newId);
	makeRectangle(10 + i * (w + 2), 10, w, h);
	run("Paste");
}
