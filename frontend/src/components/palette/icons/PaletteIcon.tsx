
import styles from "./PaletteIcon.module.css"

export type PaletteIconProps = {
    image: string,
};

export function PaletteIcon(props: PaletteIconProps) {
    return <img src={props.image} className={styles.paletteIcon} alt={"board object icon"} />
}
