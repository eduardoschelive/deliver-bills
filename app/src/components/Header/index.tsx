import { ThemeSwitcher } from '../ThemeSwitcher'

export const Header = () => {
  return (
    <header className=" py-4">
      <div className="w-11/12 flex flex-row-reverse items-center">
        <ThemeSwitcher />
      </div>
    </header>
  )
}
