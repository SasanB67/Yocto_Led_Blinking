SUMMARY = "Blink three LEDs at 1s, 2s and 3s using libgpiod"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://threeleds.c \
    file://threeleds.service \
"

S = "${WORKDIR}"

DEPENDS = "libgpiod"
RDEPENDS:${PN} = "libgpiod"

inherit systemd

SYSTEMD_SERVICE:${PN} = "threeleds.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} -o threeleds threeleds.c -lgpiod
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/threeleds ${D}${bindir}/threeleds

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/threeleds.service ${D}${systemd_system_unitdir}/threeleds.service
}

FILES:${PN} += "${systemd_system_unitdir}/threeleds.service"
