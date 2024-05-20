import clsx from 'clsx';
import React from 'react';

import { LabelModel } from '../../api';
import SectionWrapper from '../Dashboard/SectionWrapper';
type TLabel = LabelModel & { clicked: boolean };
type TLabelSectionWrapper = {
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
  className?: string;
};

export default function LabelsSectionWrapper({
  labels,
  onChangeLabels,
  className
}: TLabelSectionWrapper) {
  const prepareLabel = (label: string | undefined) => {
    if (!label) return;
    if (label.length > 20) {
      return label.slice(0, 20) + '...';
    }
    return label;
  };
  return (
    <SectionWrapper
      title="Filter by labels"
      className={clsx('text-sm w-[250px] mx-4 mb-4 mt-6 ', className)}
    >
      {labels.map((label, index) => (
        <div
          className={clsx(
            'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
            {
              'font-bold text-gray-700': label.clicked,
              'text-gray-500': !label.clicked
            }
          )}
          key={index}
          onClick={() => onChangeLabels(index)}
        >
          <span>{prepareLabel(label.label)}</span>({label.labels_count})
        </div>
      ))}
    </SectionWrapper>
  );
}
