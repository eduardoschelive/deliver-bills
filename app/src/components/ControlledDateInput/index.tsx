'use client'
import { DateUtils } from '@/utils'
import { DatePicker, DatePickerProps } from '@nextui-org/react'
import { I18nProvider } from '@react-aria/i18n'
import {
  Controller,
  FieldPath,
  FieldValues,
  Path,
  PathValue,
  useFormContext,
} from 'react-hook-form'

type ControlledDateInputProps<T extends FieldValues> = {
  name: FieldPath<T>
} & DatePickerProps

export function ControlledDateInput<T extends FieldValues>({
  name,
  ...props
}: ControlledDateInputProps<T>) {
  const { control, getFieldState } = useFormContext<T>()

  const { error, invalid } = getFieldState(name)

  const getValue = (value: PathValue<T, Path<T> & (string | undefined)>) => {
    return value ? DateUtils.convertToCalendarDate(value) : undefined
  }

  return (
    <Controller
      control={control}
      name={name}
      render={({ field }) => (
        <DatePicker
          {...props}
          errorMessage={error?.message}
          isInvalid={invalid}
          value={getValue(field.value)}
          onChange={(date) => {
            const localDate = DateUtils.convertToNativeDate(date)
            field.onChange(localDate)
          }}
        />
      )}
    />
  )
}
