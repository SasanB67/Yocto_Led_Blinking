#include <gpiod.h>
#include <unistd.h>

int main(void)
{
	unsigned int pins[3] = { 17, 27, 22 };
	enum gpiod_line_value val[3] = { 0, 0, 0 };

	struct gpiod_chip *chip = gpiod_chip_open("/dev/gpiochip0");
	struct gpiod_line_settings *s = gpiod_line_settings_new();
	struct gpiod_line_config *cfg = gpiod_line_config_new();

	gpiod_line_settings_set_direction(s, GPIOD_LINE_DIRECTION_OUTPUT);
	gpiod_line_config_add_line_settings(cfg, pins, 3, s);
	struct gpiod_line_request *req = gpiod_chip_request_lines(chip, NULL, cfg);
	if (!req)
		return 1;

	for (int t = 1; ; t++) {
		sleep(1);
		for (int i = 0; i < 3; i++)
			if (t % (i + 1) == 0)
				val[i] = !val[i];
		gpiod_line_request_set_values(req, val);
	}
}
